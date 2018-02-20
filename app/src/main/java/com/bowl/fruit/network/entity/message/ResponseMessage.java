package com.bowl.fruit.network.entity.message;

import com.bowl.fruit.network.entity.BaseResponse;

import java.util.List;

/**
 * Created by CJ on 2018/2/19.
 */

public class ResponseMessage extends BaseResponse {
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
