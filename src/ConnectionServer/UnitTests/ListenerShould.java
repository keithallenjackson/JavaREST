package ConnectionServer.UnitTests;
import Common.Framework.LogFormatter;
import Common.Logger;
import ConnectionServer.Framework.IExecutorService;
import ConnectionServer.Framework.ServerSocketWrapper;
import ConnectionServer.Listener;
import com.sun.istack.internal.NotNull;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 3/28/2016
 * File:
 * Description:
 */
public class ListenerShould {

    public class ListenerTest extends Listener {

        private boolean called = false;

        public ListenerTest(@NotNull ServerSocketWrapper wrapper,
                            @NotNull IExecutorService executor,
                            @NotNull Logger logger) {
            super(wrapper, executor, logger);
        }

        @Override
        public void handleRequest(Socket socket) {
            called = true;
        }

        public boolean handleRequestWasCalled() { return called; }
    }

    private ServerSocketWrapper serverSocketWrapper;
    private IExecutorService service;
    private ListenerTest listener;
    private Logger logger;


    @Before
    public void setup() {
        serverSocketWrapper = mock(ServerSocketWrapper.class);
        service = mock(IExecutorService.class);
        logger = new Logger(mock(OutputStream.class), mock(LogFormatter.class));
        listener = new ListenerTest(serverSocketWrapper, service, logger);

    }

    @After
    public void tearDown() {

    }

    @Test
    public void delegateIncomingConnections() throws IOException {

        // Arrange
        doAnswer((Answer<Void>) invocationOnMock -> {
            ((Runnable)invocationOnMock.getArguments()[0]).run();
            throw new IOException();
        }).when(service).execute(any(Runnable.class));

        // Act
        listener.run();

        // Assert
        verify(service, atLeast(1)).execute(any(Runnable.class));
        verify(serverSocketWrapper, atLeast(1)).accept();
        verify(service, atLeast(1)).shutdown();
        assertThat(listener.handleRequestWasCalled(), is(true));
    }



}
