package org.runelive.client.graphics.gameframe.impl;

import org.runelive.client.Client;
import org.runelive.client.RSInterface;
import org.runelive.client.Settings;
import org.runelive.client.graphics.CacheSpriteLoader;
import org.runelive.client.graphics.Canvas2D;
import org.runelive.client.graphics.fonts.RSFontSystem;
import org.runelive.client.graphics.gameframe.GameFrame;
import org.runelive.client.world.Canvas3D;

public class ChatArea extends GameFrame {

	// private int resizeY = getOffSetY();
	// private boolean isDragging = false;

	public class ChannelButtons {

		private final String[] channelText = { "All", "Game", "Public", "Private", "Clan", "Trade", "Duel",
				"Report" };
		private final int[] channelXCoords = { 5, 62, 119, 176, 233, 290, 347, 430 };
		private final String[] chatStatus = { "On", "Friends", "Off", "Hide", "All" };
		private final int[] chatTextColor = { 65280, 0xffff00, 0xff0000, 65535, 0xffff00, 65280 };
		private final String[][] chatMenuText = { { "View All" }, { "View Game" },
				{ "Hide Public", "Off Public", "Friends Public", "On Public", "View Public" },
				{ "Off Private", "Friends Private", "On Private", "View Private" },
				{ "Off Clan chat", "Friends Clan chat", "On Clan chat", "View Clan chat" },
				{ "Off Trade", "Friends Trade", "On Trade", "View Trade" },
				{ "Off Duel", "Friends Duel", "On Duel", "View Duel" } };
		private final int[][] actions = { { 999 }, { 998 }, { 997, 996, 995, 994, 993 }, { 992, 991, 990, 989 },
				{ 1003, 1002, 1001, 1000 }, { 987, 986, 985, 984 }, { 983, 982, 981, 980 } };

		public void drawChannelButtons(Client client, ScreenMode screenMode) {
			if (screenMode != ScreenMode.FIXED) {
				CacheSpriteLoader.getCacheSprite(4).drawSprite(getOffSetX() + 5, getOffSetY() + 143);
			}

			switch (client.cButtonCPos) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 20:
				CacheSpriteLoader.getCacheSprite(1).drawSprite(getOffSetX() + channelXCoords[client.cButtonCPos],
						getOffSetY() + 143);
				break;
			}
			if (client.cButtonHPos == client.cButtonCPos) {
				switch (client.cButtonHPos) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 20:
					CacheSpriteLoader.getCacheSprite(2).drawSprite(getOffSetX() + channelXCoords[client.cButtonHPos],
							getOffSetY() + 143);
					break;
				case 7:
					break;
				}
			} else {
				switch (client.cButtonHPos) {
				case 0:
				case 1:
				case 2:
				case 3:
				case 4:
				case 5:
				case 6:
				case 20:
					CacheSpriteLoader.getCacheSprite(0).drawSprite(getOffSetX() + channelXCoords[client.cButtonHPos],
							getOffSetY() + 143);
					break;
				case 7:
					break;
				}
			}

			if (client.inSprite(false, CacheSpriteLoader.getCacheSprite(17), getxPos() + 404, getyPos() + 143)) {
				CacheSpriteLoader.getCacheSprite(17).drawSprite(getOffSetX() + 404, getOffSetY() + 143);
			}

			for (int i = 0; i < channelText.length; i++) {
				client.smallText.drawCenteredText(0xffffff, getOffSetX() + channelXCoords[i] + 28, channelText[i],
						getOffSetY() + (i < 2 || i == 7 ? 157 : 154), true);
			}

			for (int i = 0; i < chatStatus.length; i++) {
				client.smallText.drawCenteredText(chatTextColor[getChatStatus(i, client)],
						getOffSetX() + 142 + channelXCoords[i], chatStatus[getChatStatus(i, client)],
						getOffSetY() + 164, true);
			}
		}

		/**
		 * Gets the chat status
		 *
		 * @param i
		 * @param client
		 * @return
		 */
		private final int getChatStatus(int i, Client client) {
			return i == 0 ? client.publicChatMode
					: i == 1 ? client.privateChatMode
							: i == 2 ? /**
										 *
										 *
										 *
										 *
										 *
										 *
										 * clan chatmode to add
										 */
									0 : i == 3 ? client.tradeMode : client.duelStatus;
		}

		public void processChatModeActions(final Client client, ScreenMode screenMode) {
			if (isVisible()) {
				for (int i = 0; i < channelXCoords.length - 1; i++) {
					if (client.inSprite(false, CacheSpriteLoader.getCacheSprite(0), getxPos() + channelXCoords[i],
							getyPos() + 143)) {

						client.cButtonHPos = i;
						client.setInputTaken(true);
						break;
					} else {
						client.cButtonHPos = -1;
						client.setInputTaken(true);
					}
				}
				/*
				 * if (clickSplitChatSelectionBox(client)) { return; }
				 */
				if (client.clickMode3 == 1 && client.clickInRegion(404, Client.clientHeight - 23, 515, Client.clientHeight)) {
					//client.takeScreenShot();
					//TODO: Report option
				}

				if (client.mouseInRegion(404, 515, Client.clientHeight - 23, Client.clientHeight)) {
					client.menuActionName[client.menuActionRow] = "Report an issue";
					client.menuActionID[client.menuActionRow] = 1414;
					client.menuActionRow++;
				}

				if(GameFrame.getScreenMode() == ScreenMode.FIXED) {
					if (client.mouseInRegion(495, 508, 458, 470)) {
						client.menuActionName[client.menuActionRow] = "Teleports";
						client.menuActionID[client.menuActionRow] = 10399;
						client.menuActionRow++;
					}
				}


				for (int i = 0; i < chatMenuText.length; i++) {
					if (client.inSprite(false, CacheSpriteLoader.getCacheSprite(0), getxPos() + channelXCoords[i],
							getyPos() + 143)) {
						for (int id = 0; id < chatMenuText[i].length; id++) {
							client.menuActionName[client.menuActionRow] = chatMenuText[i][id];
							client.menuActionID[client.menuActionRow] = actions[i][id];
							client.menuActionRow++;
						}
					}
				}
			}
		}

	}

	public ChannelButtons channel = new ChannelButtons();

	public ChatArea(int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
	}

	@Override
	public boolean isHovering(Client client, ScreenMode screenMode) {
		if (!isVisible()) {
			return false;
		}
		if (client.cButtonHPos != -1) {
			return true;
		}
		if (screenMode != ScreenMode.FIXED && client.mouseX > getxPos() + 478 && client.mouseX < getxPos() + 512
				&& client.mouseY > getyPos() + 4 && client.mouseY < getyPos() + 121) {
			return true;
		}
		if (screenMode != ScreenMode.FIXED
				&& client.inSprite(false, CacheSpriteLoader.getCacheSprite(3), getxPos(), getyPos())
				&& (client.messagePromptRaised || client.aString844 != null || client.backDialogID != -1
						|| client.dialogID != -1 | client.inputDialogState != 0)) {
			return true;
		}
		if (client.inSprite(false, CacheSpriteLoader.getCacheSprite(17), getxPos() + 404, getyPos() + 143)) {
			return true;
		}
		if (screenMode == ScreenMode.FIXED
				&& client.inSprite(false, CacheSpriteLoader.getCacheSprite(3), getOffSetX(), getOffSetY())) {
			return true;
		}
		return false;
	}

	@Override
	protected void render(Client client, ScreenMode screenMode) {
		if (isVisible()) {
			if (client.chatAreaIP == null)
				return;
			if (screenMode == ScreenMode.FIXED) {
				if (client.loggedIn) {
					client.chatAreaIP.initDrawingArea();
				}
			}

			Canvas3D.lineOffsets = client.anIntArray1180;

			if (screenMode == ScreenMode.FIXED) {
				CacheSpriteLoader.getCacheSprite(3).drawSprite(getOffSetX(), getOffSetY());
				CacheSpriteLoader.getCacheSprite(708).getResizedSprite(CacheSpriteLoader.getCacheSprite(708), 16, 16).drawSprite(getOffSetX() + 495, getOffSetY() + 120);
			} else {
				if (!componentHidden()) {
					if (!client.messagePromptRaised && client.aString844 == null && client.backDialogID == -1
							&& client.dialogID == -1 && client.inputDialogState == 0) {

						Canvas2D.fillRect(getOffSetX() + 7, getOffSetY() + 5, getWidth() - 13, 1,
								screenMode == ScreenMode.FIXED ? 0x807660 : 0xaea799, 150);
						Canvas2D.drawAlphaGradient(7, 7 + getOffSetY(), 505, 130, 0, 0x4F4F4F, 70);
					} else {
						CacheSpriteLoader.getCacheSprite(3).drawTransparentSprite(getOffSetX(), getOffSetY(), 255);
					}
				}
			}
			channel.drawChannelButtons(client, screenMode);

			if (client.messagePromptRaised) {
				client.newBoldFont.drawCenteredString(client.promptMessage, 259 + getOffSetX(), 60 + getOffSetY(), 0,
						-1);
				client.newBoldFont.drawCenteredString(client.promptInput + "*", 259 + getOffSetX(), 80 + getOffSetY(),
						128, -1);
			} else if (client.inputDialogState == 1 || client.inputDialogState == 2) {
				client.newBoldFont.drawCenteredString(client.inputTitle != null ? client.inputTitle : "Enter amount:",
						getOffSetX() + 259, getOffSetY() + 60, 0, -1);
				client.newBoldFont.drawCenteredString(client.amountOrNameInput + "*", 259 + getOffSetX(),
						80 + getOffSetY(), 128, -1);
			} else if (client.inputDialogState == 3) {
				client.getGrandExchange().displayItemSearch();
			} else if (client.aString844 != null) {
				client.newBoldFont.drawCenteredString(client.aString844, 259 + getOffSetX(), 60 + getOffSetY(), 0, -1);
				client.newBoldFont.drawCenteredString("Click to continue", 259 + getOffSetX(), 80 + getOffSetY(), 128,
						-1);
			} else if (client.backDialogID != -1) {
				client.drawInterface(getOffSetX(), 0, RSInterface.interfaceCache[client.backDialogID],
						getOffSetY() + 20);
			} else if (client.dialogID != -1) {
				client.drawInterface(getOffSetX(), 0, RSInterface.interfaceCache[client.dialogID], getOffSetY() + 20);
			} else if (!componentHidden()) {
				RSFontSystem textDrawingArea = client.newRegularFont;
				int messageY = -3;
				int scrollPosition = 0;
				Canvas2D.setBounds(getOffSetX() + 8, getOffSetY() + 10, getOffSetX() + getWidth() - 22, getOffSetY() + getHeight() - 28);

				for (int i = 0; i < 500; i++) {
					if (client.chatMessages[i] != null) {
						int chatType = client.chatTypes[i];
						int positionY = 70 - messageY * 14 + Client.anInt1089 + 6;
						String name = client.chatNames[i];
						String prefixName = name;
						byte playerRights = 0;
						int ironman = 0;


						if (name != null && name.indexOf("@") == 0) {
							int substringLength = Client.getClient().getPrefixSubstringLength(name);
							name = name.substring(substringLength);
							playerRights = client.getPrefixRights(prefixName.substring(0, prefixName.indexOf(name)),
									new Boolean(substringLength == 6));
							if (playerRights > 11) {
								ironman = playerRights - 11;
								playerRights = 0;
								// System.out.println(""+ironman);
							}
						}

						// Don't show Private messages in "All" if split chat is
						// enabled.
						if (client.chatTypeView == 0 && chatType >= 5 && chatType <= 7 && client.splitPrivateChat == 1) {
							continue;
						}

						// Private
						if (client.chatTypeView == 2 && chatType != 3 && chatType != 6 && chatType != 7) {
							continue;
						}

						// Game
						if (client.chatTypeView == 5 && chatType != 0) {
							continue;
						}

						// Trade
						if (client.chatTypeView == 3 && chatType != 4) {
							continue;
						}

						if (client.chatTypeView == 4 && chatType != 8) {
							continue;
						}

						// Clanchat
						if (client.chatTypeView == 11 && chatType != 16) {
							continue;
						}

						// System.out.println(client.chatTypeView);
						// System.out.println(chatType + ": " +
						// client.chatMessages[i]);

						/**
						 * SendMessages
						 */
						if (chatType == 0) {
							if (client.chatTypeView == 5 || client.chatTypeView == 0) {
								if (positionY > 0 && positionY < 210) {
									int xPos = 11;
									textDrawingArea.drawBasicString(client.chatMessages[i], xPos + getOffSetX(),
											positionY + getOffSetY(), screenMode == ScreenMode.FIXED ? 0 : 0xffffff,
											screenMode == ScreenMode.FIXED ? -1 : 0x000000, true);
								}

								scrollPosition++;
								messageY++;
							}
						}

						/**
						 * Normal chat
						 */
						if ((chatType == 1 || chatType == 2) && (chatType == 1 || client.publicChatMode == 0
								|| client.publicChatMode == 1 && client.isFriendOrSelf(name))) {
							if (client.chatTypeView == 1 || client.chatTypeView == 0
									|| playerRights > 0 && playerRights <= 4 && playerRights != 3) {
								if (positionY > 0 && positionY < 210) {
									int xPos = 9;
									int xPos2 = 0;
									int offsetY = 0;
									switch (playerRights) {
									case 7:
									case 8:
									case 9:
									case 10:
									case 11:
									case 19:
										offsetY = offsetY + 1;
										xPos2 += 2;
										xPos -= 1;
										break;
									case 17:
									case 16:
									case 14:
									case 6:
									case 3:
									case 20:
										// xPos += 5;
										break;
									case 5:
										offsetY -= 1;
										xPos += 1;
										xPos2 -= 1;
										break;
									}
									// xPos += 5;
									if (playerRights > 0) {
										client.modIcons[playerRights].drawTransparentSprite(xPos + xPos2 + getOffSetX(),
												positionY - 11 + getOffSetY() - offsetY, 255);
										xPos += 11;
									} else if (playerRights == 0 && ironman > 0) {
										client.modIcons[11 + ironman].drawTransparentSprite(xPos + xPos2 + getOffSetX(),
												positionY - 11 + getOffSetY() - offsetY, 255);
										xPos += 10;
									}

									String title = client.chatTitles[i] == null || client.chatTitles[i].isEmpty() ? ""
											: client.chatTitles[i];
									title = title.trim();
									int position = 0;
									if (!title.isEmpty()) {
										xPos += 0;
										position = client.chatPosition[i];
									}
									if (title != null) {
										textDrawingArea.drawBasicString(" @or3@"+title, xPos + getOffSetX(),
												positionY + getOffSetY(), client.chatColor[i], -1, true);
										if(title.contains("@")) {
											title = title.split("@")[2];
										}
										xPos += textDrawingArea.getTextWidth(" "+title) + 2;
										textDrawingArea.drawBasicString("</shad>"+name + ":", xPos + getOffSetX(),
												positionY + getOffSetY(), screenMode == ScreenMode.FIXED ? 0 : 0xffffff,
												screenMode == ScreenMode.FIXED ? -1 : 0x000000, true);
										xPos += textDrawingArea.getTextWidth(name + ":") + 2;
										textDrawingArea.drawBasicString(client.chatMessages[i], xPos + getOffSetX(),
												positionY + getOffSetY(),
												screenMode == ScreenMode.FIXED ? 255 : 0x7FA9FF,
												screenMode == ScreenMode.FIXED ? -1 : 0x000000, true);
									} else if(title == null) {
										textDrawingArea.drawBasicString("</shad>"+name, xPos + getOffSetX(),
												positionY + getOffSetY(), screenMode == ScreenMode.FIXED ? 0 : 0xffffff,
												screenMode == ScreenMode.FIXED ? -1 : 0x000000, true);
										xPos += textDrawingArea.getTextWidth(name) + 2;
										textDrawingArea.drawBasicString("<col=000000>:</col> " + client.chatMessages[i],
												xPos + getOffSetX(), positionY + getOffSetY(),
												screenMode == ScreenMode.FIXED ? 255 : 0x7FA9FF,
												screenMode == ScreenMode.FIXED ? -1 : 0x000000, true);
									}
								}
								scrollPosition++;
								messageY++;
							}
						}
						if ((chatType == 3 || chatType == 7) && (chatType == 7 || client.privateChatMode == 0
								|| client.privateChatMode == 1 && client.isFriendOrSelf(name))) {
							if (client.chatTypeView == 2 || client.chatTypeView == 0 && client.splitPrivateChat == 0) {
								if (positionY > 0 && positionY < 210) {
									int xPos = 8;
									textDrawingArea.drawBasicString("From", xPos + getOffSetX(),
											positionY + getOffSetY(), screenMode == ScreenMode.FIXED ? 0 : 0xffffff,
											screenMode == ScreenMode.FIXED ? -1 : 0, true);
									xPos += textDrawingArea.getTextWidth("From ");

									if (playerRights > 0) {
										int yoffset = 0;
										int xoffset = 0;
										switch (playerRights) {
										case 1:
										case 2:
										case 3:
										case 6:
										case 20:
											xoffset += 2;
											break;
										case 7:
										case 8:
										case 9:
										case 10:
										case 11:
										case 19:
											yoffset += 1;
											break;
										case 5:
											yoffset -= 1;
											break;
										}
										client.modIcons[playerRights].drawTransparentSprite(
												xPos + getOffSetX() - xoffset, positionY - 11 + getOffSetY() - yoffset,
												255);
										xPos += 11;
									} else if (playerRights == 0 && ironman > 0) {
										client.modIcons[11 + ironman].drawTransparentSprite(xPos + getOffSetX() - 2,
												positionY - 11 + getOffSetY(), 255);
										xPos += 10;
									}
									if (playerRights == 5) {
										xPos += 4;
									}
									if (playerRights == 4) {
										xPos += 2;
									}
									textDrawingArea.drawBasicString("</shad>"+name + ":", xPos + getOffSetX(),
											positionY + getOffSetY(), screenMode == ScreenMode.FIXED ? 0 : 0xffffff,
											screenMode == ScreenMode.FIXED ? -1 : 0, true);
									xPos += textDrawingArea.getTextWidth(name) + 8;
									textDrawingArea.drawBasicString(client.chatMessages[i], xPos + getOffSetX(),
											positionY + getOffSetY(),
											screenMode == ScreenMode.FIXED ? 0x800000 : 0xFF5256,
											screenMode == ScreenMode.FIXED ? -1 : 0x000000, true);
								}

								scrollPosition++;
								messageY++;
							}
						}
						if (chatType == 4
								&& (client.tradeMode == 0 || client.tradeMode == 1 && client.isFriendOrSelf(name))) {
							if (client.chatTypeView == 3 || client.chatTypeView == 0) {
								if (positionY > 0 && positionY < 210) {
									textDrawingArea.drawBasicString(name + " " + client.chatMessages[i],
											11 + getOffSetX(), positionY + getOffSetY(),
											screenMode == ScreenMode.FIXED ? 0x800080 : 0xFF00D4,
											screenMode == ScreenMode.FIXED ? -1 : 0, false);
								}

								scrollPosition++;
								messageY++;
							}
						}
						if (chatType == 5 && client.splitPrivateChat == 0 && client.privateChatMode < 2) {
							if (client.chatTypeView == 2 || client.chatTypeView == 0) {
								if (positionY > 0 && positionY < 210) {
									textDrawingArea.drawBasicString(client.chatMessages[i], 11 + getOffSetX(),
											positionY + getOffSetY(),
											screenMode == ScreenMode.FIXED ? 0x800000 : 0xFF5256,
											screenMode == ScreenMode.FIXED ? -1 : 0x000000, true);
								}

								scrollPosition++;
								messageY++;
							}
						}
						/**
						 * Private messaging
						 */
						if (chatType == 6 && client.privateChatMode < 2) {
							if (client.chatTypeView == 2 || client.chatTypeView == 0) {
								if (positionY > 0 && positionY < 210) {
									textDrawingArea.drawBasicString("To " + name + ":", 11 + getOffSetX(),
											positionY + getOffSetY(), screenMode == ScreenMode.FIXED ? 0 : 0xffffff,
											screenMode == ScreenMode.FIXED ? -1 : 0, true);
									textDrawingArea.drawBasicString(client.chatMessages[i],
											15 + textDrawingArea.getTextWidth("To :" + name) + getOffSetX(),
											positionY + getOffSetY(),
											screenMode == ScreenMode.FIXED ? 0x800000 : 0xFF5256,
											screenMode == ScreenMode.FIXED ? -1 : 0, true);
								}

								scrollPosition++;
								messageY++;
							}
						}
						if (chatType == 8
								&& (client.duelStatus == 0 || client.duelStatus == 1 && client.isFriendOrSelf(name))) {
							if (client.chatTypeView == 4 || client.chatTypeView == 0) {
								if (positionY > 0 && positionY < 210) {
									textDrawingArea.drawBasicString(name + " " + client.chatMessages[i],
											11 + getOffSetX(), positionY + getOffSetY(), 0x7e3200, -1, true);
								}

								scrollPosition++;
								messageY++;
							}
						}

						if (chatType == 16) {
							if (client.chatTypeView == 11 || client.chatTypeView == 0) {
								if (positionY > 0 && positionY < 210) {
									int positionX = 11;
									// String title = (screenMode ==
									// ScreenMode.FIXED ? "<col=0000FF>" :
									// "<col=7FA9FF>") + client.clanName +
									// "</col>";
									// String username = (client.chatRights[i] >
									// 0 ? "<img=" + client.chatRights[i] + "> "
									// : "") +
									// Client.capitalize(client.chatNames[i]);
									String message = (screenMode == ScreenMode.FIXED ? "<col=800000>" : "<col=FF5256>")
											+ client.chatMessages[i] + "</col>";
									textDrawingArea.drawBasicString("" + message, positionX, positionY + getOffSetY(),
											screenMode == ScreenMode.FIXED ? 0 : 0xffffff,
											screenMode == ScreenMode.FIXED ? -1 : 0, true);
								}

								scrollPosition++;
								messageY++;
							}
						}
					}
				}

				Canvas2D.defaultDrawingAreaSize();
				Client.anInt1211 = scrollPosition * 14 + 7 + 5;

				if (Client.anInt1211 < 111) {
					Client.anInt1211 = 111;
				}

				client.drawScrollbar(114, Client.anInt1211 - Client.anInt1089 - 113, getOffSetY() + 7,
						getOffSetX() + 495, Client.anInt1211, false, screenMode != ScreenMode.FIXED);

				if (Client.myPlayer != null && Client.myPlayer.name != null) {

					int drawOffsetX = getOffSetX() + getOffSetX() + 8;
					int drawOffsetY = getOffSetY() + 133;
					if (client.myRights > 0) {
						int crown = client.myRights;
						int yOffset = 0;
						int xOffset = 0;
						switch (crown) {
						case 7:
						case 8:
						case 9:
						case 10:
						case 11:
						case 19:
							xOffset = 1;
							break;
						case 17:
						case 16:
						case 14:
						case 6:
						case 3:
						case 20:
							drawOffsetX += 2;
							xOffset -= 2;
							break;
						case 5:
							drawOffsetX += 3;
							xOffset -= 3;
							yOffset += 2;
							break;
						}
						client.modIcons[crown].drawTransparentSprite(drawOffsetX + 1 + xOffset,
								getOffSetY() + 133 - 11 + yOffset, 255);
						drawOffsetX += 11;
					} else if (client.myRights == 0 && client.ironman > 0) {
						client.modIcons[11 + client.ironman].drawTransparentSprite(drawOffsetX + 1,
								getOffSetY() + 133 - 11, 255);
						drawOffsetX += 10;
					}
					Canvas2D.setDrawingArea(140 + getOffSetY(), 8, 509, 120 + getOffSetY());
					drawOffsetX += 2;
					if(Client.myPlayer.title == null) {
						textDrawingArea.drawBasicString("@bla@</shad>"+Client.myPlayer.name + ":", drawOffsetX, drawOffsetY,
								screenMode == ScreenMode.FIXED ? 0 : 0xffffff, screenMode == ScreenMode.FIXED ? -1 : 0, true);
						drawOffsetX += textDrawingArea.getTextWidth(Client.myPlayer.name);
					} else {
						textDrawingArea.drawBasicString(Client.myPlayer.title +" @bla@</shad>"+Client.myPlayer.name + ":", drawOffsetX, drawOffsetY,
								screenMode == ScreenMode.FIXED ? 0 : 0xffffff, screenMode == ScreenMode.FIXED ? -1 : 0, true);
						drawOffsetX += textDrawingArea.getTextWidth(Client.myPlayer.title + " " + Client.myPlayer.name) + 2;
					}
					textDrawingArea.drawBasicString(" " + RSFontSystem.handleOldSyntax(client.inputString) + "*",
							drawOffsetX, drawOffsetY, getScreenMode() == ScreenMode.FIXED ? 255 : 0x7fa9ff, screenMode == ScreenMode.FIXED ? -1 : 0, false);
					Canvas2D.defaultDrawingAreaSize();
				}

				// drawSplitChatSelectionBox(client);

				Canvas2D.fillRect(getOffSetX() + 7, getOffSetY() + 121, getWidth() - 13, 1,
						screenMode == ScreenMode.FIXED ? 0x807660 : 0xaea799, 150);
			}

			if (client.menuOpen && client.menuScreenArea == 2) {
				client.drawMenu();
			}

			if (screenMode == ScreenMode.FIXED) {
				client.chatAreaIP.drawGraphics(getyPos(), client.graphics, getxPos());
			}

			client.gameScreenIP.initDrawingArea();
			Canvas3D.lineOffsets = client.anIntArray1182;
		}
	}

	public static final int[] SPLIT_CHAT_COLORS = new int[] { 65535, // cyan
			// default
			// color
			16758784, // orange
			2861308, // darkish blue
			14942335, // sort of a pinkish red
			12458710, // purple
			16777215, // white
			0, // black
			51456, // green
			16711680, // red
			16776960, // yellow
			12500669, // gray
	};

	private boolean clickSplitChatSelectionBox(Client client) {
		int splitBoxX = 495;
		int splitBoxY = 122 + (GameFrame.getScreenMode() == ScreenMode.FIXED ? 0 : 2);
		if (client.getClickMode2() == 1 && client.mouseX >= splitBoxX && client.mouseX <= splitBoxX + 16
				&& client.mouseY >= getyPos() + splitBoxY && client.mouseY <= getyPos() + splitBoxY + 13) {
			client.setClickMode2(0);
			client.splitChatColor = client.splitChatColor + 1 == SPLIT_CHAT_COLORS.length ? 0
					: client.splitChatColor + 1;
			Settings.save();
			//client.pushMessage("You've changed your private split-chat color.", 0, "", myPlayer.title);
			return true;
		}
		return false;
	}

	private void drawSplitChatSelectionBox(Client client) {
		try {
			int splitBoxX = 495;
			int splitBoxY = 122 + (GameFrame.getScreenMode() == ScreenMode.FIXED ? 0 : 2);
			Canvas2D.fillRect(splitBoxX + 1, getOffSetY() + splitBoxY + 1, 15, 12,
					SPLIT_CHAT_COLORS[client.splitChatColor], 255);
			Canvas2D.fillPixels(splitBoxX, 16, 13, 0, getOffSetY() + splitBoxY);
		} catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
			client.splitChatColor = 0;
		}
	}

	public void toggleButton(Client client, int button) {
		if (GameFrame.getScreenMode() == ScreenMode.FIXED) {
			return;
		}
		if (client.cButtonCPos == button) {
			setHideComponent(!componentHidden());
		} else {
			setHideComponent(false);
		}
	}

}