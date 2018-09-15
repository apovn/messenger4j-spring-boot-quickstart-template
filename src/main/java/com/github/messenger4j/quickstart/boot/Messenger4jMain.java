package com.github.messenger4j.quickstart.boot;

import com.github.messenger4j.Messenger;
import com.github.messenger4j.exception.MessengerApiException;
import com.github.messenger4j.exception.MessengerIOException;
import com.github.messenger4j.send.MessagePayload;
import com.github.messenger4j.send.MessagingType;
import com.github.messenger4j.send.message.RichMediaMessage;
import com.github.messenger4j.send.message.TemplateMessage;
import com.github.messenger4j.send.message.TextMessage;
import com.github.messenger4j.send.message.richmedia.RichMediaAsset;
import com.github.messenger4j.send.message.richmedia.UrlRichMediaAsset;
import com.github.messenger4j.send.message.template.ButtonTemplate;
import com.github.messenger4j.send.message.template.button.Button;
import com.github.messenger4j.send.message.template.button.PostbackButton;
import com.github.messenger4j.send.message.template.button.UrlButton;
import com.github.messenger4j.userprofile.UserProfile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class Messenger4jMain {

    final static String PAGE_ACCESS_TOKEN = "";
    final static String APP_SECRET = "";
    final static String VERIFY_TOKEN = "";
    final static String USER_ID = "1244059599050515";
    final static String SAMPLE_IMG_URL = "https://www.voicebot.net/ImagesCommon/Icons/256x256/VoiceBot.png";

    public static void main(String[] args) throws MalformedURLException {

        try {
            final Messenger messenger = Messenger.create(PAGE_ACCESS_TOKEN, APP_SECRET, VERIFY_TOKEN);

            sendTextMsg(messenger);

            sendImageAttachByUrl(messenger);

            sendButtonTemplate(messenger);

        } catch (MessengerApiException e) {
            e.printStackTrace();
        } catch (MessengerIOException e) {
            e.printStackTrace();
        }

    }

    private static void sendButtonTemplate(Messenger messenger) throws MalformedURLException, MessengerApiException, MessengerIOException {
        final String recipientId = USER_ID;

        final UrlButton buttonA = UrlButton.create("Show Website", new URL("https://google.com"));
        final PostbackButton buttonB = PostbackButton.create("Start Chatting", "USER_DEFINED_PAYLOAD");

        final List<Button> buttons = Arrays.asList(buttonA, buttonB);
        final ButtonTemplate buttonTemplate = ButtonTemplate.create("What do you want to do next?", buttons);

        final TemplateMessage templateMessage = TemplateMessage.create(buttonTemplate);
        final MessagePayload payload = MessagePayload.create(recipientId, MessagingType.RESPONSE,
                templateMessage);

        messenger.send(payload);
    }

    private static void sendImageAttachByUrl(Messenger messenger) throws MalformedURLException, MessengerApiException, MessengerIOException {
        final String recipientId = USER_ID;
        final String imageUrl = SAMPLE_IMG_URL;

        final UrlRichMediaAsset richMediaAsset = UrlRichMediaAsset.create(RichMediaAsset.Type.IMAGE, new URL(imageUrl));
        final RichMediaMessage richMediaMessage = RichMediaMessage.create(richMediaAsset);
        final MessagePayload payload = MessagePayload.create(recipientId, MessagingType.RESPONSE,
                richMediaMessage);

        messenger.send(payload);
    }


    private static void sendTextMsg(Messenger messenger) throws MessengerApiException, MessengerIOException {
        final String recipientId = USER_ID;
        final UserProfile userProfile = messenger.queryUserProfile(USER_ID);
        final String text = "Hello " + userProfile.firstName() + " " + userProfile.lastName() + ". This is text message test.";
        final MessagePayload payload = MessagePayload.create(recipientId, MessagingType.RESPONSE, TextMessage.create(text));
        messenger.send(payload);
    }


}
