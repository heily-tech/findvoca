package server;

import dbconn.DBConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain {
    private Charset charset = Charset.forName("UTF-8");
    private ExecutorService executorService;
    private ServerSocketChannel serverSocketChannel; //클라이언트 연결을 수락하는 ServerSocketChannel 필드 선언
    private List<Client> connections = new Vector<Client>();
    private DBConnection db;

    ServerMain() {
        db = new DBConnection();
        Connection connection = db.getConnection();
        if (connection != null) {
            System.out.println("데이터베이스 연결 성공");
            // 여기에서 추가적인 작업을 수행할 수 있습니다.
        } else {
            System.out.println("데이터베이스 연결 실패");
            // 연결에 실패한 경우에 대한 처리를 수행할 수 있습니다.
        }
    }

    void startServer() { //서버 시작 코드
        executorService = Executors.newFixedThreadPool(1000); //CPU 코어 수에 맞게 스레드를 생성해서 관리하는 ExcutorService를 생성

        System.out.println("[서버 시작]");
        try {
            serverSocketChannel = ServerSocketChannel.open(); //ServerSocketChannel을 정적 메소드인 open()으로 생성
            serverSocketChannel.configureBlocking(true); //기본적으로 블로킹 방식으로 동작하지만, 명시적으로 설정한다.
            //serverSocketChannel.bind(new InetSocketAddress("jdeok.iptime.org", 5001)); // IP(도메인)및 바인딩포트 적용해 서버소켓을 구성
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 3007)); // IP(도메인)및 바인딩포트 적용해 서버소켓을 구성
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("이미 사용되고 있는 포트번호 입니다."); //해당 포트를 이미 다른 프로그램에서 사용하고 있을때.
            if(serverSocketChannel.isOpen())
                //stopServer(); //서버종료
                return; //startServer() 메소드 종료
        }

        //ServerSocketChannel은 반복해서 클라이언트 연결 요청을 기다려야 하므로 스레드풀의 작업스레드상에서 accept() 메소드를 반복적으로 호출해주어야한다.
        Runnable runnable = new Runnable() { //연결 수락 작업을 Runnable로 정의
            public void run() {
                while(true) {
                    try {
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        String message1 = "[연결 수락: " + socketChannel.getRemoteAddress() + ": " + Thread.currentThread().getName() + "]";
                        System.out.println(message1);

                        Client client = new Client(socketChannel);
                        connections.add(client);
                        String message2 = "[연결 개수: " + connections.size() + "]";
                        System.out.println(message2);

                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int byteCount = socketChannel.read(byteBuffer);
                        if (byteCount == -1) {
                            throw new IOException();
                        }
                        byteBuffer.flip();
                        String message = charset.decode(byteBuffer).toString();
                        receiveMessage(client, message);

                    } catch(Exception e) {
                        if(serverSocketChannel.isOpen())
                            break;
                    }
                }
            };
        };
        executorService.submit(runnable); //연결 수락 작업을 스레드풀에서 처리하기 위해 excutorService의 submit()을 호출한다.
    }

    void handleClientMessage(Client client, String message) {
        System.out.println("hadleClientMessage" + message);
        if (message.startsWith("@login")) {
            String[] parts = message.split("@");
            if (parts.length == 4) { // 형식: @login@username@password
                String username = parts[2];
                String password = parts[3];

                boolean isLoggedIn = db.login(username, password);
                if (isLoggedIn) {
                    String successMessage = "@login.success";
                    client.send(successMessage);
                } else {
                    String failureMessage = "@login.failed";
                    client.send(failureMessage);
                }
            }
        } else if (message.startsWith("@signup")) {
            String[] parts = message.split("@");
            if (parts.length == 5) { // 형식: @signup@username@password@nickname
                String username = parts[2];
                String password = parts[3];
                String nickname = parts[4];

                boolean isRegistered = db.signup(username, password, nickname);
                if (isRegistered) {
                    String successMessage = "@signup.success";
                    client.send(successMessage);
                } else {
                    String failureMessage = "@signup.failed";
                    client.send(failureMessage);
                }
            }
        } else if (message.startsWith("@withdrawal")) {
            String[] parts = message.split("@");
            if (parts.length == 3) { // 형식: @withdrawal@learnerID
                String learnerID = parts[2];
                boolean isUnregisterd = db.withdrawal(learnerID);
                if (isUnregisterd){
                    String successMessage = "@withdrawal.success";
                    client.send(successMessage);
                } else {
                    String failureMessage = "@withdrawal.failed";
                    client.send(failureMessage);
                }
            }
        } else if (message.startsWith("@learnerInfo")) {
            String[] parts = message.split("@");
            if (parts.length == 3) { // 형식 : @learnerInfo@learnerID
                String learnerID = parts[2];
                String nickname = db.getNickname(learnerID);
                client.send("@learnerInfo." + nickname);
            }
        } else if (message.startsWith("@learnerVoca")) {
            String[] parts = message.split("@");
            if (parts.length == 3) {
                String learnerID = parts[2];
                List<String> vocaNames = db.getVocaNames(learnerID);
                String vocaDatum = String.join(",", vocaNames);
                client.send("@learnerVoca." + vocaDatum);
            }
        }
    }

    void receiveMessage(Client client, String message) {
        handleClientMessage(client, message);
        while (client.socketChannel.isOpen()) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try {
                int byteCount = client.socketChannel.read(byteBuffer);
                if (byteCount == -1) {
                    client.stopClient();
                    break;
                }
                byteBuffer.flip();
                String newMessage = charset.decode(byteBuffer).toString();
                handleClientMessage(client, newMessage);
            } catch (IOException e) {
                client.stopClient();
                break;
            }
        }
    }

    void stopServer() { //서버 종료 코드
        try {
            Iterator<Client> iterator = connections.iterator(); //connections 컬렉션으로부터 반복자를 얻어낸다.
            while(iterator.hasNext()) { //while문으로 반복자를 반복
                Client client = iterator.next(); //Client를 하나씩 얻는다.
                client.socketChannel.close(); //Client가 가지고 있는 SocketChannel을 닫는다.
                iterator.remove(); //connections 컬렉션에서 Client를 제거
            }

            if(serverSocketChannel!=null && serverSocketChannel.isOpen()) //serverSocketChannel이 null이 아니고 열려있으면
                serverSocketChannel.close(); //serverSocketChannel을 닫는다.

            if(executorService!=null && !executorService.isShutdown()) //excutorService가 null이 아니고 종료상태가 아니면
                executorService.shutdown(); //excutorService를 종료한다.


            System.out.println("[서버 멈춤]");
        } catch (Exception e) {}
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        ServerMain sm = new ServerMain();
        sm.startServer();
    }

}