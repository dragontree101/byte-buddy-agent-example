package com.dragon.study.bytebuddy.redis;

import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.ClassFileLocator;
import net.bytebuddy.pool.TypePool;

/**
 * Created by dragon on 16/4/1.
 */
public class RedisTypeDescription {

  public static TypeDescription commandDescription(ClassFileLocator.Compound compound) {
    return TypePool.Default.of(compound).describe("redis.clients.jedis.Protocol$Command").resolve();
  }

  public static TypeDescription byteArrayArrayDescription() {
    return new TypeDescription.Latent(new TypeDescription.ForLoadedType(byte[][].class).getName(), TypeDescription.ARRAY_MODIFIERS, null, null);
  }
}
