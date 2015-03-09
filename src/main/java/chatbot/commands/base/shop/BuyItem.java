package chatbot.commands.base.shop;

import chatbot.commands.base.AbstractCommand;
import chatbot.core.PatternConstants;
import chatbot.orchestration.ShoppingOrchestrationService;
import chatbot.services.ShoppingService;
import chatbot.services.ViewerService;
import org.apache.commons.lang3.StringUtils;
import org.pircbotx.hooks.events.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;

/**
 * Created by matthias6 on 06.03.2015.
 */
public class BuyItem extends AbstractCommand {

    private final ShoppingOrchestrationService shoppingOrchestrationService;

    @Autowired
    public BuyItem(final ShoppingOrchestrationService shoppingOrchestrationService){
        this.shoppingOrchestrationService = shoppingOrchestrationService;
    }

    @Override
    protected void executeCommand(MessageEvent event) {
        String[] messageParts = StringUtils.split(event.getMessage(), " ");
        String itemName = StringUtils.join(Arrays.copyOfRange(messageParts, 1, messageParts.length));
        shoppingOrchestrationService.buyItem(event.getUser().getNick(), itemName);

    }

    @Override
    protected boolean isCommandUnderstood(String message) {
        String[] split = StringUtils.split(message, " ");
        return split.length >= 2 && StringUtils.equals(split[0], PatternConstants.PREFIX + "buy");
    }
}