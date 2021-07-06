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

public class CreateTeamPacket extends GenericTeamPacket {

  private String displayName;
  private int friendlyFlags;
  private String nameTagVisibility;
  private String collisionRule;
  private int teamColor;
  private String teamPrefix;
  private String teamSuffix;
  private Set<String> entities;

  /**
   * Creates a new instance.
   */
  public CreateTeamPacket() {
    super(ActionType.CREATE_TEAM);
  }

  /**
   * Creates a new instance.
   *
   * @param name the name of the team to create
   */
  public CreateTeamPacket(String name) {
    super(ActionType.CREATE_TEAM, name);
  }

  /**
   * Creates a new instance.
   *
   * @param name the name of the team to create
   * @param displayName the displayed name of the team to create
   * @param friendlyFlags the friendly flags of the team to create
   * @param nameTagVisibility the name tag visibility of the team to create
   * @param collisionRule the collision rule of the team to create
   * @param teamColor the color of the team to create
   * @param teamPrefix the prefix of the team to create
   * @param teamSuffix the suffix of the team to create
   * @param entities the entries in the team to create
   */
  public CreateTeamPacket(String name, String displayName, int friendlyFlags, String nameTagVisibility,
      String collisionRule, int teamColor, String teamPrefix, String teamSuffix, Set<String> entities) {
    super(ActionType.CREATE_TEAM, name);

    this.displayName = displayName;
    this.friendlyFlags = friendlyFlags;
    this.nameTagVisibility = nameTagVisibility;
    this.collisionRule = collisionRule;
    this.teamColor = teamColor;
    this.teamPrefix = teamPrefix;
    this.teamSuffix = teamSuffix;
    this.entities = entities;
  }

  @Override
  public void encode(ByteBuf buf, ProtocolUtils.Direction direction, ProtocolVersion protocolVersion) {
    ProtocolUtils.writeString(buf, getName());
    buf.writeByte(getAction().getAction());
    ProtocolUtils.writeString(buf, displayName);
    buf.writeByte(friendlyFlags);
    ProtocolUtils.writeString(buf, nameTagVisibility);
    ProtocolUtils.writeString(buf, collisionRule);
    ProtocolUtils.writeVarInt(buf, teamColor);
    ProtocolUtils.writeString(buf, teamPrefix);
    ProtocolUtils.writeString(buf, teamSuffix);
    ProtocolUtils.writeVarInt(buf, entities.size());
    for (String entity : entities) {
      ProtocolUtils.writeString(buf, entity);
    }
  }

  public String getDisplayName() {
    return displayName;
  }

  public int getFriendlyFlags() {
    return friendlyFlags;
  }

  public String getNameTagVisibility() {
    return nameTagVisibility;
  }

  public String getCollisionRule() {
    return collisionRule;
  }

  public int getTeamColor() {
    return teamColor;
  }

  public String getTeamPrefix() {
    return teamPrefix;
  }

  public String getTeamSuffix() {
    return teamSuffix;
  }

  public Set<String> getEntities() {
    return entities;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public void setFriendlyFlags(int friendlyFlags) {
    this.friendlyFlags = friendlyFlags;
  }

  public void setNameTagVisibility(String nameTagVisibility) {
    this.nameTagVisibility = nameTagVisibility;
  }

  public void setCollisionRule(String collisionRule) {
    this.collisionRule = collisionRule;
  }

  public void setTeamColor(int teamColor) {
    this.teamColor = teamColor;
  }

  public void setTeamPrefix(String teamPrefix) {
    this.teamPrefix = teamPrefix;
  }

  public void setTeamSuffix(String teamSuffix) {
    this.teamSuffix = teamSuffix;
  }

  public void setEntities(Set<String> entities) {
    this.entities = entities;
  }

  @Override
  public String toString() {
    return "CreateTeamPacket{"
        + "displayName='" + displayName + '\''
        + ", friendlyFlags=" + friendlyFlags
        + ", nameTagVisibility=" + nameTagVisibility
        + ", collisionRule=" + collisionRule
        + ", teamColor=" + teamColor
        + ", teamPrefix='" + teamPrefix + '\''
        + ", teamSuffix='" + teamSuffix + '\''
        + ", entities=" + entities
        + '}';
  }

  @Override
  public boolean handle(MinecraftSessionHandler handler) {
    return handler.handle(this);
  }
}