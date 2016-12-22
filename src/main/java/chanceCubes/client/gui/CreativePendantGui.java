package chanceCubes.client.gui;

import chanceCubes.CCubesCore;
import chanceCubes.containers.CreativePendantContainer;
import chanceCubes.network.CCubesPacketHandler;
import chanceCubes.network.PacketCreativePendant;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CreativePendantGui extends GuiContainer {

    private static final ResourceLocation guiTextures = new ResourceLocation(CCubesCore.MODID + ":textures/gui/container/guiCreativePendant.png");
    private static CreativePendantContainer container;
    private int chanceValue = 0;
    private EntityPlayer player;

    public CreativePendantGui(EntityPlayer player, World world) {
        super(container = new CreativePendantContainer(player.inventory, world));
        this.xSize = 176;
        this.ySize = 167;
        this.player = player;
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 0)
            this.chanceValue -= 1;
        else if (button.id == 1)
            this.chanceValue += 1;
        else if (button.id == 2)
            this.chanceValue -= 5;
        else if (button.id == 3)
            this.chanceValue += 5;
        else if (button.id == 4)
            this.chanceValue -= 10;
        else if (button.id == 5)
            this.chanceValue += 10;
        else if (button.id == 6 && container.getChanceCubesInPendant() != null)
            CCubesPacketHandler.INSTANCE.sendToServer(new PacketCreativePendant(this.player.getCommandSenderEntity().getName(), this.chanceValue));

        if (this.chanceValue > 100)
            this.chanceValue = 100;
        if (this.chanceValue < -100)
            this.chanceValue = -100;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(guiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        this.fontRendererObj.drawString("Chance Value", 50, 5, 0);
        String cValue = "" + this.chanceValue;
        this.fontRendererObj.drawString(cValue, (88 - (cValue.length() * 3)), 27, 0);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui() {
        super.initGui();
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 40, (this.height / 2) - 63, 20, 20, I18n.format("-1", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 15, (this.height / 2) - 63, 20, 20, I18n.format("+1", new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 60, (this.height / 2) - 63, 20, 20, I18n.format("-5", new Object[0])));
        this.buttonList.add(new GuiButton(3, this.width / 2 + 35, (this.height / 2) - 63, 20, 20, I18n.format("+5", new Object[0])));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 80, (this.height / 2) - 63, 20, 20, I18n.format("-10", new Object[0])));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 55, (this.height / 2) - 63, 20, 20, I18n.format("+10", new Object[0])));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 12, (this.height / 2) - 35, 70, 20, I18n.format("Set Chance", new Object[0])));
    }
}