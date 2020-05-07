package com.example.webfluxmongodb.model.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import com.example.webfluxmongodb.sender.MailSender;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

@Slf4j
public class NotificationActor extends AbstractActor {

    private final String mail;
    private final String taskName;
    private final String taskDescription;
    private final Date performanceDate;
    private final MailSender mailSender;

    public NotificationActor(String mail, String taskName, String taskDescription, Date performanceDate, MailSender mailSender) {
        this.mail = mail;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.performanceDate = performanceDate;
        this.mailSender = mailSender;
    }

    public static Props props(String mail, String taskName, String taskDescription, Date performanceDate, MailSender mailSender) {
        return Props.create(NotificationActor.class, mail, taskName, taskDescription, performanceDate, mailSender);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchEquals(
                        "schedule",
                        m -> {
                            if (new Date().getTime() >= performanceDate.getTime()) {
                                mailSender.send(
                                        mail,
                                        "It's time to start our task!",
                                        String.format("Hello! We remind you that it is time to complete the following task: %s. Task description: %s.", taskName, taskDescription)
                                );
                                getSelf().tell(PoisonPill.getInstance(), ActorRef.noSender());
                            }
                        })
                .build();
    }
}
