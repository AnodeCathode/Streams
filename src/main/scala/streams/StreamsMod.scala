package streams

import farseek.FarseekBaseMod
import farseek.block._
import farseek.world._
import net.minecraft.block.Block._
import net.minecraft.block.material.{Material, MaterialLiquid}
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.Mod.EventHandler
import net.minecraftforge.fml.common.event.{FMLLoadCompleteEvent, FMLPreInitializationEvent}
import streams.block._
import streams.world.gen.structure.RiverGenerator

/** @author delvr */
@Mod(modid = "Streams", modLanguage = "scala")
object StreamsMod extends FarseekBaseMod {

    val name = "Streams"
    val description = "Adds flowing rivers to the world."
    val authors = Seq("delvr")
    val configuration = None

    override val requiresNewWorld = false
    override val existingWorldWarning = Some("Can cause unexpected behavior such as incomplete rivers.")

    @EventHandler override def handle(event: FMLPreInitializationEvent) {
        super.handle(event)
        FixedFlowBlock.getClass // Register river blocks
        RiverGenerator.surfaceWaterGenerator = new RiverGenerator(Material.WATER.asInstanceOf[MaterialLiquid], SurfaceDimensionId)
    }

    @EventHandler override def handle(event: FMLLoadCompleteEvent) {
        super.handle(event)
        val customWaterIds = allBlocks.filter(_.material == Material.WATER).map(getIdFromBlock(_)).toSeq.sorted.map("mc_Entity.x == " + _).mkString( " || ")
        info("Welcome to Farseek/Streams. Please visit http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/2346379-streams-real-flowing-rivers for information and updates.")
        info("Shader configuration: if you wish to use shaders with custom water blocks including Streams river blocks, edit your shader's gbuffers_water.vsh file to replace the line:\n" +
                s"if (mc_Entity.x == 8 || mc_Entity.x == 9) {\n" +
                "with the following line:\n" +
                s"if ($customWaterIds) {\n" +
                "Note that these ids will change with every new combination of loaded mods.")
    }
}
