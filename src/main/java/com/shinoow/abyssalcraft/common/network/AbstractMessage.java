/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.network;

import java.io.IOException;

import com.shinoow.abyssalcraft.AbyssalCraft;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

/**
 *
 * Creating an abstract message class similar to {@link Packet} allows us to take
 * advantage of Minecraft's PacketBuffer class, which has many useful methods.
 *
 * Should the IMessageHandler be implemented as a GenericMessageHandler class,
 * every child message class will still have to have an empty implementation of
 * the handler, just so it can be registered:
 *
 * public static class Handler extends GenericMessageHandler<SomeMessage> {}
 *
 * This is kind of ridiculous, so instead the message will also implement the handler,
 * despite the fact that a handler instance shouldn't have any of the class members or
 * methods that a message instance does (since a handler is not a message).
 *
 * To combat that incongruity, the #onMessage method will be made final.
 *
 * As a bonus, registration of this class can be made less verbose than normal, since the
 * same class is used for both the IMessage and the IMessageHandler.
 *
 */
public abstract class AbstractMessage<T extends AbstractMessage<T>> implements IMessage, IMessageHandler <T, IMessage>
{
	/**
	 * Some PacketBuffer methods throw IOException - default handling propagates the exception.
	 * if an IOException is expected but should not be fatal, handle it within this method.
	 */
	protected abstract void read(PacketBuffer buffer) throws IOException;

	/**
	 * Some PacketBuffer methods throw IOException - default handling propagates the exception.
	 * if an IOException is expected but should not be fatal, handle it within this method.
	 */
	protected abstract void write(PacketBuffer buffer) throws IOException;

	/**
	 * Called on whichever side the message is received;
	 * for bidirectional packets, be sure to check side
	 */
	public abstract void process(EntityPlayer player, Side side);

	/**
	 * If message is sent to the wrong side, an exception will be thrown during handling
	 * @return True if the message is allowed to be handled on the given side
	 */
	protected boolean isValidOnSide(Side side) {
		return true; // default allows handling on both sides, i.e. a bidirectional packet
	}

	/**
	 * Whether this message requires the main thread to be processed (i.e. it
	 * requires that the world, player, and other objects are in a valid state).
	 */
	protected boolean requiresMainThread() {
		return true;
	}

	@Override
	public void fromBytes(ByteBuf buffer) {
		try {
			read(new PacketBuffer(buffer));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void toBytes(ByteBuf buffer) {
		try {
			write(new PacketBuffer(buffer));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	//=====================================================================//
	/*
	 * Make the implementation final so child classes don't need to bother
	 * with it, since the message class shouldn't have anything to do with
	 * the handler. This is simply to avoid having to have:
	 *
	 * public static class Handler extends GenericMessageHandler<OpenGuiMessage> {}
	 *
	 * in every single message class for the sole purpose of registration.
	 */
	@Override
	public final IMessage onMessage(T msg, MessageContext ctx) {
		if (!msg.isValidOnSide(ctx.side))
			throw new RuntimeException("Invalid side " + ctx.side.name() + " for " + msg.getClass().getSimpleName());
		else if(msg.requiresMainThread())
			checkThreadAndEnqueue(msg, ctx);
		else msg.process(AbyssalCraft.proxy.getPlayerEntity(ctx), ctx.side);
		return null;
	}

	/**
	 * Ensures that the message is being handled on the main thread
	 */
	private static final <T extends AbstractMessage<T>> void checkThreadAndEnqueue(final AbstractMessage<T> msg, final MessageContext ctx) {
		IThreadListener thread = AbyssalCraft.proxy.getThreadFromContext(ctx);
		if (!thread.isCallingFromMinecraftThread())
			thread.addScheduledTask(() -> msg.process(AbyssalCraft.proxy.getPlayerEntity(ctx), ctx.side));
	}

	/**
	 * Messages that can only be sent from the server to the client should use this class
	 */
	public static abstract class AbstractClientMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(Side side) {
			return side.isClient();
		}
	}

	/**
	 * Messages that can only be sent from the client to the server should use this class
	 */
	public static abstract class AbstractServerMessage<T extends AbstractMessage<T>> extends AbstractMessage<T> {
		@Override
		protected final boolean isValidOnSide(Side side) {
			return side.isServer();
		}
	}
}
