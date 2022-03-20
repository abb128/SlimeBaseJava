package slime.base.proto;

import slime.base.proto.handlers.*;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;

public class HandlerMapper {
    private static Map<UUID, BaseHandlerFactory> factories = new HashMap<UUID, BaseHandlerFactory>();

    public static BaseHandler createHandlerOrNull(UUID command, ClientSessionAttorney attorney){
        BaseHandlerFactory factory = factories.get(command);
        if(factory == null) return null;

        return factory.create(attorney);
    }

    public static void add(BaseHandlerFactory factory){
        HandlerMapper.factories.put(factory.getUUID(), factory);
    }

    public static void init(){
        HandlerMapper.factories = new HashMap<UUID, BaseHandlerFactory>();

        HandlerMapper.add(new HandshakeHandlerFactory());
        HandlerMapper.add(new HandshakeInitHandlerFactory());
    }
}