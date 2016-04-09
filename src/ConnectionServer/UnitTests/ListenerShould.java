package ConnectionServer.UnitTests;
import ConnectionServer.Framework.IExecutorService;
import ConnectionServer.Framework.OnAcceptListener;
import ConnectionServer.Framework.ServerSocketWrapper;
import ConnectionServer.Listener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;

import java.io.IOException;
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

    private ServerSocketWrapper serverSocketWrapper;
    private IExecutorService service;
    private OnAcceptListener lambda;
    private Listener listener;


    @Before
    public void setup() {
        serverSocketWrapper = mock(ServerSocketWrapper.class);
        service = mock(IExecutorService.class);
        lambda = mock(OnAcceptListener.class);
        listener = new Listener(serverSocketWrapper, lambda, service);
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
        verify(lambda, atLeast(1)).Handle(any(Socket.class));
    }



}
