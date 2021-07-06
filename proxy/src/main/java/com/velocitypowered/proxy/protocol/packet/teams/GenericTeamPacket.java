/*
 * Copyright (C) 2018 Velocity Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.velocitypowered.proxy.protocol.packet.teams;

import com.velocitypowered.api.network.ProtocolVersion;
import com.velocitypowered.proxy.protocol.MinecraftPacket;
import com.velocitypowered.proxy.protocol.ProtocolUtils;

import io.netty.buffer.ByteBuf;

public abstract class GenericTeamPacket implements MinecraftPacket {

  public enum ActionType {

    CREATE_TEAM(0),
    REMOVE_TEAM(1),
    UPDATE_TEAM(2),
    ADD_ENTITIES_TO_TEAM(3),
    REMOVE_ENTITIES_FROM_TEAM(4);

    private final int action;

    ActionType(int action) {
      this.action = action;
    }

    public int getAction() {
      return action;
    }
  }

  private final ActionType action;
  private String name;

  public GenericTeamPacket(ActionType action) {
    this(action, null);
  }

  public GenericTeamPacket(ActionType action, String name) {
    this.action = action;
    this.name = name;
  }

  public ActionType getAction() {
    return action;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public void decode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion protocolVersion) {
    throw new UnsupportedOperationException(); // encode only
  }

  /**
   * Constructs a new team packet.
   *
   * @param type the type of packet to create
   * @param name the name of the team which will
   *             be created, modified or removed
   * @return the created team packet
   */
  public static GenericTeamPacket constructTeamPacket(ActionType type, String name) {
    switch (type) {
      case CREATE_TEAM:
        return new CreateTeamPacket(name);
      case REMOVE_TEAM:
        return new RemoveTeamPacket(name);
      case UPDATE_TEAM:
        return new UpdateTeamPacket(name);
      case ADD_ENTITIES_TO_TEAM:
        return new AddEntitiesToTeamPacket(name);
      case REMOVE_ENTITIES_FROM_TEAM:
        return new RemoveEntitiesFromTeamPacket(name);
      default:
        throw new IllegalArgumentException("Invalid ActionType");
    }
  }
}