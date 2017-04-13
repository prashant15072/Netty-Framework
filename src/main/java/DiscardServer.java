import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by PRASHANT on 4/13/2017.
 */
public class DiscardServer {
    private int port;

    public DiscardServer(int port){
        this.port=port;
    }

    public void run() throws Exception{
        EventLoopGroup bossGroup=new NioEventLoopGroup();
        EventLoopGroup workerGroup=new NioEventLoopGroup();
        try {
            ServerBootstrap b =new ServerBootstrap();
            b.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel channel) throws Exception {
                            channel.pipeline().addLast(new DiscardHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            ChannelFuture f=b.bind(port).sync();
            f.channel().closeFuture().sync();

        }
        finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String args[]) throws Exception {
        int port=16;
        new DiscardServer(port).run();
    }
}
