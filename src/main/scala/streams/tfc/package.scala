package streams


import farseek.block.BlockAndData
import farseek.util._
import farseek.world._
import java.util.Random

import net.dries007.tfc.objects.blocks.BlocksTFC
import net.dries007.tfc.objects.entity.EntityFallingBlockTFC
import net.dries007.tfc.world.classic.worldgen.WorldGenFissure
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.BlockPos.PooledMutableBlockPos
import net.minecraft.world._
import streams.block.BlockRiver
import streams.world.gen.structure.RiverGenerator._

package object tfc {


  def generate(generator: WorldGenFissure, world: World, random: Random, x: Int, y: Int, z: Int) {

    val pos = PooledMutableBlockPos.retain
    if(between(y, y+10).forall(!world.getBlockState(pos.setPos(x, y, z)).getMaterial.isLiquid)
      generator.generate(world, random, x, y, z)
  } //random: Random, chunkX: Int, chunkZ: Int, world: World, chunkGenerator: IChunkGenerator, chunkProvider: IChunkProvider

    def generateStreams(generator: net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider, world: World, xChunk: Int, zChunk: Int, blocks: Array[Block], datas: Array[Byte]) {
        surfaceWaterGenerator.onChunkGeneration(world.asInstanceOf[WorldServer], generator, xChunk, zChunk, blocks, datas)
    } //world: WorldServer, generator: IChunkGenerator, xChunk: Int, zChunk: Int, primer: ChunkPrimer

    def tfcSurfaceBlockAt(wxz: XZ, world: World, sedimentOnly: Boolean = false): BlockAndData = {
        val biome = world.getBiomeGenForCoords(wxz.x, wxz.z)
        val rain = getRainfall(world, wxz.x, 0, wxz.z)
        val soilData = getCacheManager(world).getRockLayerAt(wxz.x, wxz.z, 0).data1
        val soilBlock =
            if(biome == BEACH || biome == OCEAN || biome == DEEP_OCEAN) getTypeForSand(soilData)
            else if(biome == GRAVEL_BEACH || sedimentOnly) getTypeForGravel(soilData)
            else getTypeForGrassWithRain(soilData, rain)
        (soilBlock, getSoilMeta(soilData))
    }

    def isFreshWater(block: Block): Boolean = block.isInstanceOf[BlockRiver] || BlocksTFC.isFreshWater(block.getDefaultState)

    def canReplace(fallingBlock: EntityFallingBlockTFC, world: World, x: Int, y: Int, z: Int): Boolean = {
        if(!blockAt(x, y, z)(world).isInstanceOf[BlockRiver])
            fallingBlock.canReplace(world, x, y, z)
        else {
            fallingBlock.shouldDropItem = true
            false
        }
    }
}
