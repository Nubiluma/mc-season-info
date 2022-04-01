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
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

@Mod("seasoninfo")
public class SeasonInfo {

    private final MatrixStack matrixStack = new MatrixStack();

    public SeasonInfo() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRender(TickEvent.RenderTickEvent event) {
        if (isInGame()) {
            ISeasonState clientSeasonState = SeasonHelper.dataProvider.getClientSeasonState();
            String seasonName = capitalize(clientSeasonState.getSeason());
            int day = clientSeasonState.getDay();
            int daysLeftUntilNextSeason = (clientSeasonState.getSeasonDuration() / 24_000) - day;
            int screenHeight = Minecraft.getInstance().getWindow().getGuiScaledHeight();

            drawText(seasonName + ", Day: " + day, 1, (screenHeight - 20));
            drawText(daysLeftUntilNextSeason + " days left until next season: ", 1, (screenHeight - 10));
        }
    }

//    @SubscribeEvent
//    public void onTickEvent(TickEvent.WorldTickEvent worldTickEvent){
//        long worldDayTime = (worldTickEvent.world.getDayTime() / 24_000);
//        drawText("Day time: " + worldDayTime, 1, 40);
//        long worldGameTime = worldTickEvent.world.getGameTime();
//        drawText("Game time: " + worldGameTime, 1, 50);
//    }

    private boolean isInGame() {
        Screen screen = Minecraft.getInstance().screen;
        return screen == null || screen instanceof ChatScreen;
    }

    private void drawText(String text, float x, float y) {
        int fallbackBlackColor = 0;
        int color = TextFormatting.WHITE.getColor() == null ? fallbackBlackColor : TextFormatting.WHITE.getColor();
        Minecraft.getInstance().font.draw(matrixStack, text, x, y, color);
    }

    private String capitalize(Season season) {
        return season.name().charAt(0) + season.name().substring(1).toLowerCase();
    }

}
