package streams.core

import farseek.core._
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex

/** @author delvr */
@SortingIndex(value = FarseekCoreModSortIndex + 1)
class StreamsCoreMod extends FarseekBaseCoreMod {

    protected val transformerClasses = Seq(classOf[StreamsClassTransformer])
}

class StreamsClassTransformer extends MethodReplacementTransformer {

    implicit private val transformer = this

    protected val methodReplacements = Seq(
        MethodReplacement("net/minecraft/block/BlockLiquid", "getFlowDirection", "func_185697_a",
            "(Lnet/minecraft/world/IBlockAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/material/Material;)F",
            "streams/block/FixedFlowBlockExtensions/getFlowDirection"),
        MethodReplacement("net/minecraft/entity/item/EntityBoat", "getUnderwaterStatus", "func_184444_v",
            "()Lnet/minecraft/entity/item/EntityBoat$Status;",
            "streams/entity/item/EntityBoatExtensions/getUnderwaterStatus"),
        MethodReplacement("net/minecraftforge/client/model/ModelLoader", "onRegisterAllBlocks",
            "(Lnet/minecraft/client/renderer/BlockModelShapes;)V",
            "streams/block/FixedFlowBlockExtensions/onRegisterAllBlocks"),
        MethodReplacement("net/minecraft/client/renderer/block/statemap/DefaultStateMapper", "getModelResourceLocation", "func_178132_a",
            "(Lnet/minecraft/block/state/IBlockState;)Lnet/minecraft/client/renderer/block/model/ModelResourceLocation;",
            "streams/block/FixedFlowBlockExtensions/getModelResourceLocation")
    )
}
