/*=========================================================================
 * Copyright (c) 2002-2014 Pivotal Software, Inc. All Rights Reserved.
 * This product is protected by U.S. and international copyright
 * and intellectual property laws. Pivotal products are covered by
 * more patents listed at http://www.pivotal.io/patents.
 *=========================================================================
 */
package com.gemstone.gemfire.cache.client.internal;

import com.gemstone.gemfire.internal.cache.tier.MessageType;
import com.gemstone.gemfire.internal.cache.tier.sockets.Message;

/**
 * Does a region size on a server
 * @author gregp
 * @since 6.6
 */
public class SizeOp {
  /**
   * Does a region size on a server using connections from the given pool
   * to communicate with the server.
   * @param pool the pool to use to communicate with the server.
   * @param region the name of the region to do the entry keySet on
   */
  public static Integer execute(InternalPool pool,
                            String region)
  {
    AbstractOp op = new SizeOpImpl(region);
    return (Integer)pool.execute(op);
  }
                                                               
  private SizeOp() {
    // no instances allowed
  }
  
  private static class SizeOpImpl extends AbstractOp {
    /**
     * @throws com.gemstone.gemfire.SerializationException if serialization fails
     */
    public SizeOpImpl(String region) {
      super(MessageType.SIZE, 1);
      getMessage().addStringPart(region);
    }

    @Override  
    protected Object processResponse(Message msg) throws Exception {
      
      return processObjResponse(msg, "size");
    }
    @Override  
    protected boolean isErrorResponse(int msgType) {
      return msgType == MessageType.SIZE_ERROR;
    }
    @Override  
    protected long startAttempt(ConnectionStats stats) {
      return stats.startSize();
    }
    @Override  
    protected void endSendAttempt(ConnectionStats stats, long start) {
      stats.endSizeSend(start, hasFailed());
    }
    @Override  
    protected void endAttempt(ConnectionStats stats, long start) {
      stats.endSize(start, hasTimedOut(), hasFailed());
    }

    @Override
    protected void processSecureBytes(Connection cnx, Message message)
        throws Exception {
    }

    @Override
    protected boolean needsUserId() {
      return false;
    }

    @Override
    protected void sendMessage(Connection cnx) throws Exception {
      getMessage().setEarlyAck((byte)(getMessage().getEarlyAckByte() & Message.MESSAGE_HAS_SECURE_PART));
      getMessage().send(false);
    }
  }
}
