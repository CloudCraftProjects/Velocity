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
import com.velocitypowered.proxy.connection.MinecraftSessionHandler;
import com.velocitypowered.proxy.protocol.ProtocolUtils;

import io.netty.buffer.ByteBuf;

import java.util.Set;

public class AddEntitiesToTeamPacket extends GenericTeamPacket {

  private Set<String> entities;

  /**
   * Creates a new instance.
   */
  public AddEntitiesToTeamPacket() {
    super(ActionType.ADD_ENTITIES_TO_TEAM);
  }

  /**
   * Creates a new instance.
   *
   * @param name the name of the team to update
   */
  public AddEntitiesToTeamPacket(String name) {
    super(ActionType.ADD_ENTITIES_TO_TEAM, name);
  }

  /**
   * Creates a new instance.
   *
   * @param name the name of the team to update
   * @param entities the entries to add
   */
  public AddEntitiesToTeamPacket(String name, Set<String> entities) {
    super(ActionType.ADD_ENTITIES_TO_TEAM, name);

    this.entities = entities;
  }

  @Override
  public void encode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion protocolVersion) {
    ProtocolUtils.writeString(buf, getName());
    buf.writeByte(getAction().getAction());
    ProtocolUtils.writeVarInt(buf, entities.size());
    for (String entity : entities) {
      ProtocolUtils.writeString(buf, entity);
    }
  }

  public Set<String> getEntities() {
    return entities;
  }

  public void setEntities(Set<String> entities) {
    this.entities = entities;
  }

  @Override
  public String toString() {
    return "AddEntitiesToTeamPacket{"
        + "entities=" + entities
        + '}';
  }

  @Override
  public boolean handle(MinecraftSessionHandler handler) {
    return handler.handle(this);
  }
}