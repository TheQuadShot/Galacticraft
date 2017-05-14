package micdoodle8.mods.galacticraft.core.client.gui.element;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.client.gui.screen.SmallFontRenderer;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import micdoodle8.mods.galacticraft.core.util.ColorUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.FMLClientHandler;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;

public class GuiElementSlider extends GuiButton
{
    private Vector3 firstColor;
    private Vector3 lastColor;
    private final boolean isVertical;
    private int sliderPos;

    public GuiElementSlider(int id, int x, int y, int width, int height, boolean vertical, Vector3 firstColor, Vector3 lastColor)
    {
        this(id, x, y, width, height, vertical, firstColor, lastColor, "");
    }

    public GuiElementSlider(int id, int x, int y, int width, int height, boolean vertical, Vector3 firstColor, Vector3 lastColor, String displayString)
    {
        super(id, x, y, width, height, displayString);
        this.isVertical = vertical;
        this.firstColor = firstColor;
        this.lastColor = lastColor;
    }

    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.visible)
        {
            this.hovered = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;

            if (Mouse.isButtonDown(0) && this.hovered)
            {
                if (this.isVertical)
                {
                    this.sliderPos = par3 - this.yPosition;
                }
                else
                {
                    this.sliderPos = par2 - this.xPosition;
                }
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_ALPHA_TEST);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glShadeModel(GL11.GL_SMOOTH);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tessellator.getWorldRenderer();

            if (this.isVertical)
            {
                worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                worldRenderer.pos((double) this.xPosition + this.width, this.yPosition, this.zLevel).color(0, 0, 0, 1.0F).endVertex();
                worldRenderer.pos(this.xPosition, this.yPosition, this.zLevel).color(0, 0, 0, 1.0F).endVertex();
                worldRenderer.pos(this.xPosition, (double) this.yPosition + this.height, this.zLevel).color(0, 0, 0, 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + this.width, (double) this.yPosition + this.height, this.zLevel).color(0, 0, 0, 1.0F).endVertex();
                tessellator.draw();

                worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                worldRenderer.pos((double) this.xPosition + this.width - 1, (double) this.yPosition + 1, this.zLevel).color(this.firstColor.floatX(), this.firstColor.floatY(), this.firstColor.floatZ(), 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + 1, (double) this.yPosition + 1, this.zLevel).color(this.firstColor.floatX(), this.firstColor.floatY(), this.firstColor.floatZ(), 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + 1, (double) this.yPosition + this.height - 1, this.zLevel).color(this.lastColor.floatX(), this.lastColor.floatY(), this.lastColor.floatZ(), 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + this.width - 1, (double) this.yPosition + this.height - 1, this.zLevel).color(this.lastColor.floatX(), this.lastColor.floatY(), this.lastColor.floatZ(), 1.0F).endVertex();
                tessellator.draw();

                worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                worldRenderer.pos((double) this.xPosition + this.width, (double) this.yPosition + this.sliderPos - 1, this.zLevel).color(1, 1, 1, 1.0F).endVertex();
                worldRenderer.pos(this.xPosition, (double) this.yPosition + this.sliderPos - 1, this.zLevel).color(1, 1, 1, 1.0F).endVertex();
                worldRenderer.pos(this.xPosition, (double) this.yPosition + this.sliderPos + 1, this.zLevel).color(1, 1, 1, 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + this.width, (double) this.yPosition + this.sliderPos + 1, this.zLevel).color(1, 1, 1, 1.0F).endVertex();
                tessellator.draw();
            }
            else
            {
                worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                worldRenderer.pos((double) this.xPosition + this.width, this.yPosition, this.zLevel).color(0, 0, 0, 1.0F).endVertex();
                worldRenderer.pos(this.xPosition, this.yPosition, this.zLevel).color(0, 0, 0, 1.0F).endVertex();
                worldRenderer.pos(this.xPosition, (double) this.yPosition + this.height, this.zLevel).color(0, 0, 0, 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + this.width, (double) this.yPosition + this.height, this.zLevel).color(0, 0, 0, 1.0F).endVertex();
                tessellator.draw();

                worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                worldRenderer.pos((double) this.xPosition + this.width - 1, (double) this.yPosition + 1, this.zLevel).color(this.lastColor.floatX(), this.lastColor.floatY(), this.lastColor.floatZ(), 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + 1, (double) this.yPosition + 1, this.zLevel).color(this.firstColor.floatX(), this.firstColor.floatY(), this.firstColor.floatZ(), 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + 1, (double) this.yPosition + this.height - 1, this.zLevel).color(this.firstColor.floatX(), this.firstColor.floatY(), this.firstColor.floatZ(), 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + this.width - 1, (double) this.yPosition + this.height - 1, this.zLevel).color(this.lastColor.floatX(), this.lastColor.floatY(), this.lastColor.floatZ(), 1.0F).endVertex();
                tessellator.draw();

                GL11.glShadeModel(GL11.GL_FLAT);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);

                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                GL11.glShadeModel(GL11.GL_SMOOTH);

                worldRenderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
                worldRenderer.pos((double) this.xPosition + this.sliderPos + 1, this.yPosition, this.zLevel).color(1, 1, 1, 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + this.sliderPos - 1, this.yPosition, this.zLevel).color(1, 1, 1, 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + this.sliderPos - 1, (double) this.yPosition + this.height, this.zLevel).color(1, 1, 1, 1.0F).endVertex();
                worldRenderer.pos((double) this.xPosition + this.sliderPos + 1, (double) this.yPosition + this.height, this.zLevel).color(1, 1, 1, 1.0F).endVertex();
                tessellator.draw();
            }

            GL11.glShadeModel(GL11.GL_FLAT);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
    }

    public void drawHoveringText()
    {
        if (this.hovered)
        {
            FontRenderer font = FMLClientHandler.instance().getClient().fontRendererObj;
            Minecraft mc = FMLClientHandler.instance().getClient();
            ScaledResolution scaledresolution = ClientUtil.getScaledRes(mc, mc.displayWidth, mc.displayHeight);
            int width = scaledresolution.getScaledWidth();
            int height = scaledresolution.getScaledHeight();
            int x = Mouse.getX() * width / mc.displayWidth;
            int y = height - Mouse.getY() * height / mc.displayHeight - 1;
            net.minecraftforge.fml.client.config.GuiUtils.drawHoveringText(Collections.singletonList(this.displayString), x, y, width, height, -1, font);
        }
    }

    public void setSliderPos(float pos)
    {
        this.sliderPos = (int) Math.floor(this.height * pos);
    }

    public int getSliderPos()
    {
        return this.sliderPos;
    }

    public float getNormalizedValue()
    {
        return this.sliderPos / (float) this.height;
    }

    public double getColorValueD()
    {
        return (this.sliderPos * 255.0D) / (this.height - 1);
    }

    public int getButtonHeight()
    {
        return this.height;
    }
}
