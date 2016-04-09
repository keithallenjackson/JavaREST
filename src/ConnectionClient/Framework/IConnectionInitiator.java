package ConnectionClient.Framework;

import java.io.IOException;

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
public interface IConnectionInitiator {
    IConnection Initialize(IConnectionInformation info, IConnectionMetaInformation meta) throws IOException;
}
