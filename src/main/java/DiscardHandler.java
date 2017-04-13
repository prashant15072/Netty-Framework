import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by PRASHANT on 4/12/2017.
 */
public class DiscardHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        /*ByteBuf a=(ByteBuf)msg;
        try {
            while (a.isReadable()){
                System.out.println((char) a.readByte());
                System.out.flush();
            }
        }
        finally {
            a.release();
        }*/
        ctx.write(msg);
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
