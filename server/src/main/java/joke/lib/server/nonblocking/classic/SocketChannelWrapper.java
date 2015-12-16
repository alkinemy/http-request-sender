package joke.lib.server.nonblocking.classic;

import lombok.Getter;
import lombok.Setter;

import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

@Getter
public class SocketChannelWrapper implements Closeable {
	private SocketChannel socketChannel;

	@Setter
	private boolean inProgress;

	private SocketChannelWrapper(SocketChannel socketChannel) {
		this.socketChannel = socketChannel;
		this.inProgress = false;
	}

	public static SocketChannelWrapper wrap(SocketChannel socketChannel) {
		return new SocketChannelWrapper(socketChannel);
	}

	public boolean isConnected() {
		return socketChannel.isConnected();
	}

	public int write(ByteBuffer src) throws IOException {
		return socketChannel.write(src);
	}

	@Override public final void close() throws IOException {
		socketChannel.close();
	}
}
