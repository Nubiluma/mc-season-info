package de.cwansart.minecraft.seasoninfo;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import sereneseasons.api.season.ISeasonState;
import sereneseasons.api.season.SeasonHelper;

@Mod("seasoninfo")
public class SeasonInfo {

    public SeasonInfo() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (isInGame()) {
            ISeasonState clientSeasonState = SeasonHelper.dataProvider.getClientSeasonState();
            String seasonName = clientSeasonState.getSeason().name();
            String subSeasonName = clientSeasonState.getSubSeason().name();
            int day = clientSeasonState.getDay();
            int cycleDuration = clientSeasonState.getCycleDuration();

            drawText(seasonName + ", " + subSeasonName, 1, 1);
            drawText("Day: " + day, 1, 10);
            drawText("Cycle duration: " + cycleDuration, 1, 20);
        }
    }

    private boolean isInGame() {
        Screen screen = Minecraft.getInstance().screen;
        return screen == null || screen instanceof ChatScreen;
    }

    private void drawText(String text, float x, float y) {
        int fallbackBlackColor = 0;
        int color = TextFormatting.WHITE.getColor() == null ? fallbackBlackColor : TextFormatting.WHITE.getColor();
        Minecraft.getInstance().font.draw(new MatrixStack(), text, x, y, color);
    }
}
