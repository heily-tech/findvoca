package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.StringTokenizer;


public class Client {
    public SocketChannel socketChannel;
    private Charset charset = Charset.forName("UTF-8");
    private boolean loginResult;
    private boolean signupResult;
    private Thread rthread;

    public Client(SocketChannel socket) {
        this.socketChannel = socket;
        loginResult = false;
        rthread = null;
    }

    public void startClient() {
        rthread = new Thread() {
            public void run() {
                try {
                    socketChannel = SocketChannel.open();
                    socketChannel.configureBlocking(true);
                    socketChannel.connect(new InetSocketAddress("127.0.0.1", 3007));

                    String message = "[연결 완료]: " + socketChannel.getRemoteAddress() + "]";
                    System.out.println(message);
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                    String message = "[서버 통신 안됨]";
                    System.out.println(message);
                    if (socketChannel.isOpen())
                        stopClient();
                    return;
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

            if (socketChannel != null && socketChannel.isOpen())
                socketChannel.close();
        } catch (IOException e) {
        }
    }

    public void receive() {
        System.out.println("Client received");
        while (true) {
            try {
                ByteBuffer byteBuffer = ByteBuffer.allocate(10000); //길이가 100으로 생성
                int readByteCount = socketChannel.read(byteBuffer);
                if (readByteCount == -1)
                    throw new IOException();
                byteBuffer.flip(); //ByteBuffer의 위치 속성값을 변경
                String data = charset.decode(byteBuffer).toString(); //UTF-8로 디코딩된 문자열을 data에 저장

                if (data.startsWith("@login")) {
                    data = data.substring(7);
                    System.out.println(data);
                    if (data.equals("success"))
                        setLoginResult(true);
                    else
                        setLoginResult(false);
                } else if (data.startsWith("@signup")) {
                    data = data.substring(8);
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