package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

public class Client {
    public SocketChannel socketChannel;
    private Charset charset = Charset.forName("UTF-8");
    private String learnerID;
    private String learnerNickname;
    private String vocaName;
    private String[] vocaNames, vocaWords, vocaMeans;
    private boolean loginResult, signupResult, withdrawalResult, deleteVocaResult, deleteWordResult, createResult;
    private Thread rthread;

    public String getLearnerID() {
        return learnerID;
    }

    public void setLearnerID(String learnerID) {
        this.learnerID = learnerID;
    }

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
                    socketChannel.connect(new InetSocketAddress("127.0.0.1", 3008));

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
            System.out.println(e.getMessage());
        }
    }

    public void receive() {
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
                } else if (data.startsWith("@withdrawal")) {
                    data = data.substring(12);
                    if (data.equals("success"))
                        setWithdrawalResult(true);
                    else
                        setWithdrawalResult(false);
                } else if (data.startsWith("@learnerInfo")) {
                    data = data.substring(13);
                    setLearnerNickname(data);
                } else if (data.startsWith("@learnerVoca")) {
                    data = data.substring(13);
                    String[] vocaNames = data.split(",");
                    setVocaNames(vocaNames);
                } else if (data.startsWith("@learnerWord")) {
                    data = data.substring(13);
                    String[] vocaWords = data.split(",");
                    setVocaWords(vocaWords);
                } else if (data.startsWith("@learnerMean")) {
                    data = data.substring(13);
                    String[] vocaMeans = data.split(",");
                    setVocaMeans(vocaMeans);
                } else if (data.startsWith("@deleteVoca")) {
                    data = data.substring(12);
                    if (data.equals("success"))
                        setDeleteVocaResult(true);
                    else
                        setDeleteVocaResult(false);
                } else if (data.startsWith("@create")) {
                    data = data.substring(8);
                    System.out.println(data);
                    if (data.equals("success"))
                        setCreateResult(true);
                    else
                        setCreateResult(false);
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

    public void setLoginResult(boolean res) {
        this.loginResult = res;
    }

    public boolean getLoginResult() {
        return this.loginResult;
    }

    public void setSignupResult(boolean res) {
        this.signupResult = res;
    }

    public boolean getSignupResult() {
        return this.signupResult;
    }

    public boolean getWithdrawalResult() {
        return withdrawalResult;
    }

    public void setWithdrawalResult(boolean res) {
        this.withdrawalResult = res;
    }
    public String getLearnerNickname() {
        return learnerNickname;
    }

    public void setLearnerNickname(String nick) {
        this.learnerNickname = nick;
    }

    public String getVocaName() {
        return vocaName;
    }

    public void setVocaName(String vocaName) {
        this.vocaName = vocaName;
    }

    public String[] getVocaNames() {
        return vocaNames;
    }

    public void setVocaNames(String[] vocaNames) {
        this.vocaNames = vocaNames;
    }

    public String[] getVocaWords() {
        return vocaWords;
    }

    public void setVocaWords(String[] vocaWords) {
        this.vocaWords = vocaWords;
    }

    public String[] getVocaMeans() {
        return vocaMeans;
    }

    public void setVocaMeans(String[] vocaMeans) {
        this.vocaMeans = vocaMeans;
    }

    public boolean getDeleteVocaResult() {
        return deleteVocaResult;
    }

    public void setDeleteVocaResult(boolean deleteVocaResult) {
        this.deleteVocaResult = deleteVocaResult;
    }

    public boolean getDeleteWordResult() {
        return deleteWordResult;
    }

    public void setDeleteWordResult(boolean deleteWordResult) {
        this.deleteWordResult = deleteWordResult;
    }

    public boolean getCreateResult() {
        return createResult;
    }

    public void setCreateResult(boolean createResult) {
        this.createResult = createResult;
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