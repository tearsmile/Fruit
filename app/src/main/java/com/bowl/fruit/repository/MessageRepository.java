package com.bowl.fruit.repository;

import com.bowl.fruit.network.FruitApi;
import com.bowl.fruit.network.FruitNetService;
import com.bowl.fruit.network.entity.message.Message;
import com.bowl.fruit.network.entity.message.ResponseMessage;
import com.bowl.fruit.preference.PreferenceDao;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by CJ on 2018/2/20.
 */

public class MessageRepository {
    private FruitApi mApi;

    public static MessageRepository instance() {
        return MessageRepository.Singleton.INSTANCE;
    }

    private static final class Singleton {
        private static final MessageRepository INSTANCE = new MessageRepository();
    }

    private MessageRepository() {
        mApi = FruitNetService.getInstance().getFruitApi();
    }

    public Observable<List<Message>> getMessage(int type, int page){
        return mApi.getMessageList(PreferenceDao.getInstance().getString("key_login_user_id",""),type, page)
                .map(new Func1<ResponseMessage, List<Message>>() {
                    @Override
                    public List<Message> call(ResponseMessage responseMessage) {
                        if(responseMessage.getCode() == 0){
                            return responseMessage.getMessages();
                        }
                        return null;
                    }
                });
    }

}
