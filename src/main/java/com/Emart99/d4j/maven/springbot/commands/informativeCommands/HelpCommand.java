package com.Emart99.d4j.maven.springbot.commands.informativeCommands;

import com.Emart99.d4j.maven.springbot.commands.Command;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.spec.EmbedCreateSpec;
import org.springframework.stereotype.Component;

public class HelpCommand implements Command {
    @Override
    public String getName() {
        return "*help";
    }

    @Override
    public void execute(MessageCreateEvent event) throws Exception {
        EmbedCreateSpec.Builder builder = EmbedCreateSpec.builder();
        builder.title("Comandos disponibles");
        builder.addField("*generateImageGpt","Genera una imagen basada en lo que le pidas mediante gpt.",false);
        builder.addField("*chatGpt","Permite realizar una conversacion con chatGpt 3.5.",false);
        builder.addField("*play","Reproduce tracks de youtube N veces, espera como input la cantidad de veces que lo quiere repetir y un link o el nombre de un video, si ya hay un track reproduciendo se agrega todo a la cola.",false);
        builder.addField("*skip","Saltea al siguiente track de la cola.",false);
        builder.addField("*seek","Adelanta el track que se esta reproduciendo actualmente a un momento especifico, espera como input horas minutos segundos, de la forma 00:00 o 00:00:00.",false);
        builder.addField("*pause","Pausa el track que se esta reproduciendo.",false);
        builder.addField("*resume","Reproduce un track previamente pausado.",false);
        builder.addField("*stop","Borra la cola y desconecta al bot del channel.",false);
        builder.addField("*shuffle","Randomiza la cola.",false);
        builder.addField("*porro","Me prendo ese \uD83D\uDE17\uD83D\uDC4C\uD83D\uDEAC.",false);
        builder.addField("*cf","Coinflip, devuelve cara o seca.",false);
        builder.addField("*cf2","Coinflip2, espera al menos 2 valores, ej: *cf2 a b.",false);
        //builder.addField("*sanguchoto","Reproduce un mitico audio del gordo agucho, en paz descanse ✝️.",false);
        //builder.addField("*aceitedecoco","Reproduce el gran audio aceite de coco by el Benha.",false);
        //builder.addField("*hitbenha","Reproduce todos versus el Benha.",false);
        builder.addField("*perown","perown.",false);
        builder.addField("*baldu","Baldu la leyenda..",false);
        builder.addField("*fuckthat","fuckthat.",false);
        builder.addField("*meme","Random meme.",false);
        event.getMessage().getChannel().block().createMessage(builder.build()).block();
    }
}
