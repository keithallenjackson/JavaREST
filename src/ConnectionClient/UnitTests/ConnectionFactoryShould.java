package ConnectionClient.UnitTests;

import ConnectionClient.Framework.IConnection;
import ConnectionClient.Framework.IConnectionInformation;
import ConnectionClient.Framework.IConnectionInitiator;
import ConnectionClient.Framework.IConnectionMetaInformation;
import ConnectionClient.ConnectionFactory;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.core.Is.*;
import static org.hamcrest.core.IsEqual.*;
import static org.hamcrest.core.IsNot.*;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

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
public class ConnectionFactoryShould {

    private IConnectionInitiator initiatorMock;
    private ConnectionFactory factory;

    @Before
    public void setUp() {
        initiatorMock = mock(IConnectionInitiator.class);
        factory = new ConnectionFactory(initiatorMock);
    }

    @Test
    public void createInstanceBasedOnConnectionInitiator() throws IOException {
        // setup
        IConnection IConnection = factory.create();

        // test
        assertThat(IConnection, is(equalTo(null)));
        verify(initiatorMock, times(1)).Initialize(any(IConnectionInformation.class),
                any(IConnectionMetaInformation.class));

    }

    @Test
    public void createInstanceBasedOnConnectionInitiatorNotNull() throws IOException {

        // Arrange
        IConnection mockIConnection = mock(IConnection.class);
        when(initiatorMock.Initialize(any(IConnectionInformation.class), any(IConnectionMetaInformation.class)))
                .thenReturn(mockIConnection);

        // Act
        IConnection ret = factory.create();

        // Assert
        assertThat(ret, is(equalTo(mockIConnection)));
        assertThat(ret, is(not(equalTo(null))));

    }

    @Test
    public void returnsNullWhenExceptionsThrownFromInitiator() throws IOException {
        // Arrange
        when(initiatorMock.Initialize(any(IConnectionInformation.class), any(IConnectionMetaInformation.class)))
                .thenThrow(IOException.class);
        // Act
        IConnection connection = factory.create();

        // Assert
        assertThat(connection, is(equalTo(null)));
    }
}