package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.StringTokenizer;


public class tcpClient {
    private SocketChannel socketChannel;
    private Charset charset = Charset.forName("UTF-8");
    private boolean loginResult;
    private boolean signupResult;
    private Thread rthread;

    //private Window window;
    public tcpClient(/*Window window*/) {
        //this.window = window;
        loginResult = false;
        rthread = null;
    }

    public void startClient() {
        rthread = new Thread() { //receive()에서 블로킹이 일어나기 때문에 새로운 작업 스레드를 생성한다.
            public void run() {
                try {
                    socketChannel = SocketChannel.open();
                    socketChannel.configureBlocking(true);
                    //socketChannel.connect(new InetSocketAddress("jdeok.iptime.org", 5001)); //해당 주소:포트로 연결
                    socketChannel.connect(new InetSocketAddress("192.168.0.5", 5006)); //해당 주소:포트로 연결

                    String message = "[연결 완료]: " + socketChannel.getRemoteAddress() + "]";

					/*if(Game.gameState != STATE.Login) { //로그인 상태가 아니면 첫실행시 자신의 닉네임을 서버에 전송 (서버에서 유저 목록 업데이트)
						String data = "@nick"+Game.userID;
						window.getTcpClient().send(data);
					}*/


                    System.out.println(message);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    String message = "[서버 통신 안됨]";
                    System.out.println(message);
//                    e.printStackTrace();
                    if (socketChannel.isOpen()) //socketChannel이 열려있으면 stopClient()호출
                        stopClient();
                    return; //스레드 종료
                }
                receive();
            }
        };
        rthread.start();
    }

    public void stopClient() {
        try {
            String message = "[TCP 연결 끊음]";
            System.out.println(message);

            if (socketChannel != null && socketChannel.isOpen()) //socketChannel 필드가 null이 아니고, 현재 열려있는 경우
                socketChannel.close(); //socketChannel을 닫는다.
        } catch (IOException e) {
        }
    }

    // startClient()에서 생성한 작업 스레드상에서 호출이 된다.
    public void receive() { //서버에서 데이터 받는 역할
        while (true) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(10000); //길이가 100으로 생성

                //서버가 비정상적으로 종료했을 경우 IOException 발생
                int readByteCount = socketChannel.read(byteBuffer);

                //서버가 정상적으로 Socket의 close()를 호출했을 경우
                if (readByteCount == -1)
                    throw new IOException();

                byteBuffer.flip(); //ByteBuffer의 위치 속성값을 변경
                String data = charset.decode(byteBuffer).toString(); //UTF-8로 디코딩된 문자열을 data에 저장
                if (data.matches("^@login.*")) {
                    data = data.substring(6, data.length()); //@login문장을 제거하고 저장
                    StringTokenizer userListDate = new StringTokenizer(data, "@", false); //@를  구분자로 둔다.
                    String loginResult[] = new String[userListDate.countTokens()]; //구분자의 개수만큼 배열크기 설정

                    //System.out.println(data);;

                    if (data.equals("success"))
                        setLoginResult(true);
                    else
                        setLoginResult(false);
                } else if (data.matches("^@signup.*")) {
                    data = data.substring(7, data.length()); //@signup문장을 제거하고 저장
                    StringTokenizer userListDate = new StringTokenizer(data, "@", false); //@를  구분자로 둔다.
                    String signupResult[] = new String[userListDate.countTokens()]; //구분자의 개수만큼 배열크기 설정

                    //System.out.println(data);

                    if (data.equals("success"))
                        setSignupResult(true);
                    else
                        setSignupResult(false);
                }
            } catch (Exception e) {
                String message = "[서버 통신 안됨]";
                System.out.println(message);
                e.printStackTrace();
                stopClient();
                break;
            }
        }
    }

    public void setLoginResult(boolean result) {
        this.loginResult = result;
    }

    public boolean getLoginResult() {
        System.out.println(this.loginResult);
        return this.loginResult;
    }

    public void setSignupResult(boolean result) {
        this.signupResult = result;
    }

    public boolean getSignupResult() {
        return this.signupResult;
    }

    public void send(String data) {
        Thread thread = new Thread() {
            public void run() {
                try {
                    ByteBuffer byteBuffer = charset.encode(data);
                    socketChannel.write(byteBuffer);
                    String message = "[보내기 완료]";
                    System.out.println(message);

                } catch (Exception e) {
                    String message = "[서버 통신 안됨]";
                    System.out.println(message);
                    e.printStackTrace();
                    stopClient();
                }
            }
        };
        thread.start();
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }
}