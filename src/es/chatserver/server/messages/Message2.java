
package es.chatserver.server.messages;

/**
 *
 * @author adrinfer
 */
public class Message2 {
    
    
    private String userFrom;
    private String userTo;
    
    private String messageContent;
    
    
    public Message2(String userFrom, String userTo, String messageContent)
    {
        this.userFrom = userFrom;
        this.userTo = userTo;
        this.messageContent = messageContent;
    }
    
    
    public void setUserFrom(String userFrom)
    {
        this.userFrom = userFrom;
    }
    
    public String getUserFrom()
    {
        return this.userFrom;
    }
    
    public void setUserTo(String userTo)
    {
        this.userTo = userTo;
    }
    
    public String getUserTo()
    {
        return this.userTo;
    }
    
    public void setMessageContent(String messageContent)
    {
        this.messageContent = messageContent;
    }
    
    public String getMessageContent()
    {
        return this.messageContent;
    }
}
