package com.huto.hutosmod.dimension.alagadda;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.huto.hutosmod.dimension.endTemplate.EndGenTemplate;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraft.world.gen.MapGenCavesHell;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenFire;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenHellLava;
import net.minecraft.world.gen.structure.MapGenNetherBridge;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.terraingen.ChunkGeneratorEvent.InitNoiseField;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextEnd;
import net.minecraftforge.event.terraingen.InitNoiseGensEvent.ContextHell;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.Event;

@SuppressWarnings("unused")
public class AlagadaGenOld implements IChunkGenerator
{
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
    protected static final IBlockState BEDROCK = Blocks.GRASS.getDefaultState();
    
    //Block that is usually Netherrack
    protected static final IBlockState MAIN_BLOCK = Blocks.BOOKSHELF.getDefaultState();
    //Block that is usally Lava
    protected static final IBlockState YOUR_LIQUID = Blocks.AIR.getDefaultState();
    //Blocks that are usally gravel and soul sand
    protected static final IBlockState OTHER_BLOCK1 = Blocks.BROWN_GLAZED_TERRACOTTA.getDefaultState();
    protected static final IBlockState OTHER_BLOCK2 = Blocks.LOG.getDefaultState();
    
    private final World world;
    private final boolean generateStructures;
    private final Random rand;
    
    private double[] slowsandNoise = new double[256], gravelNoise = new double[256], depthBuffer = new double[256], buffer;
    private NoiseGeneratorOctaves lperlinNoise1, lperlinNoise2, perlinNoise1, slowsandGravelNoiseGen, netherrackExculsivityNoiseGen, scaleNoise, depthNoise;
    double[] pnr, ar, br, dr, noiseData4;
    private static NoiseGeneratorSimplex islandNoise;

	private final WorldGenFire fireFeature = new WorldGenFire();
    private final WorldGenGlowStone1 lightGemGen = new WorldGenGlowStone1();
    private final WorldGenGlowStone2 hellPortalGen = new WorldGenGlowStone2();
    private final WorldGenBush brownMushroomFeature = new WorldGenBush(Blocks.BROWN_MUSHROOM);
    private final WorldGenBush redMushroomFeature = new WorldGenBush(Blocks.RED_MUSHROOM);
    private MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
    
    private MapGenBase genNetherCaves = new MapGenCavesHell();
    private final WorldGenHellLava hellSpringGen = new WorldGenHellLava(Blocks.FLOWING_WATER, false);

    public AlagadaGenOld(World world, boolean generate, long seed)
    {
        this.world = world;
        this.generateStructures = generate;
        this.rand = new Random(seed);
        world.setSeaLevel(63);

        this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 4);
        this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 36);
        this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 2);
        this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.rand, 14);
        this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
        this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 7);
        this.depthNoise = new NoiseGeneratorOctaves(this.rand, 12);

        ContextHell ctx = new ContextHell(lperlinNoise1, lperlinNoise2, perlinNoise1, slowsandGravelNoiseGen, netherrackExculsivityNoiseGen, scaleNoise, scaleNoise);
        ContextEnd ctxE = new ContextEnd(lperlinNoise1, lperlinNoise2, perlinNoise1, scaleNoise, scaleNoise, islandNoise);
        ctx = TerrainGen.getModdedNoiseGenerators(world, this.rand, ctx);
        ctxE= TerrainGen.getModdedNoiseGenerators(world, this.rand, ctxE);

        this.lperlinNoise1 = ctx.getLPerlin1();
        this.lperlinNoise2 = ctx.getLPerlin2();
        this.perlinNoise1 = ctx.getPerlin();
        this.slowsandGravelNoiseGen = ctx.getPerlin2();
        this.netherrackExculsivityNoiseGen = ctx.getPerlin3();
        this.scaleNoise = ctx.getScale();
        this.depthNoise = ctx.getDepth();
        this.islandNoise = ctxE.getIsland();
        
    }
    
    public static float getIslandHeightValue(int x, int z, int p_185960_3_, int p_185960_4_)
    {
        float f = (float)(x * 2 + p_185960_3_);
        float f1 = (float)(z * 2 + p_185960_4_);
        float f2 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * 8.0F;

        if (f2 > 80.0F)
        {
            f2 = 80.0F;
        }

        if (f2 < -100.0F)
        {
            f2 = -100.0F;
        }

        for (int i = -12; i <= 12; ++i)
        {
            for (int j = -12; j <= 12; ++j)
            {
                long k = (long)(x + i);
                long l = (long)(z + j);

                if (k * k + l * l > 4096L && AlagadaGenOld.islandNoise.getValue((double)k, (double)l) < -0.8999999761581421D)
                {
                    float f3 = (MathHelper.abs((float)k) * 3439.0F + MathHelper.abs((float)l) * 147.0F) % 13.0F + 9.0F;
                    f = (float)(p_185960_3_ - i * 2);
                    f1 = (float)(p_185960_4_ - j * 2);
                    float f4 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * f3;

                    if (f4 > 80.0F)
                    {
                        f4 = 80.0F;
                    }

                    if (f4 < -100.0F)
                    {
                        f4 = -100.0F;
                    }

                    if (f4 > f2)
                    {
                        f2 = f4;
                    }
                }
            }
        }

        return f2;
    }
    @SuppressWarnings("static-access")
 		public boolean isIslandChunk(int x, int z)
 	    {
 	        return (long)x * (long)x + (long)z * (long)z > 4096L && this.getIslandHeightValue(x, z, 1, 1) >= 0.0F;
 	    }

    public void prepareHeights(int x, int z, ChunkPrimer primer)
    {
    	Random rand = new Random();
        int i = 1;
        int j = this.world.getSeaLevel();
        int k = 5;
        int l = 5;
        int i1 = 4;
        //SO with the big pillars the smaller the 2 to last value the taller the pillers but DOTN make it smaller than the other 2 values
        this.buffer = this.getHeights(this.buffer, x * 1, 0, z * 1, 6, 17, 6);
        for (int j1 = 0; j1 < 1; ++j1)
        {
        	//The bigger this number the bigger the pillars are
            for (int k1 = 0; k1 < 1; ++k1)
            {
                for (int l1 = 0; l1 < 32; ++l1)
                {
                    double d0 = 0.225D;
                    //This takes some form of notch out of the floor ones
                //    double d1 = d0;
                   double d1 = this.buffer[((j1 + 0) * 5 + k1 + 0) * 17 + l1 + 0];
                    //This takes some form of notch out of the celing ones
                    double d2 = this.buffer[((j1 + 0) * 5 + k1 + 1) * 17 + l1 + 0];
                    //No Idea
                    double d3 = this.buffer[((j1 + 1) * 5 + k1 + 0) * 17 + l1 + 0];
                    double d4 = this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 0];
                    double d5 = (this.buffer[((j1 + 0) * 5 + k1 + 0) * 17 + l1 + 1] - d1) * 0.125D;
                    double d6 = (this.buffer[((j1 + 0) * 5 + k1 + 1) * 17 + l1 + 1] - d2) * 0.125D;
                    double d7 = (this.buffer[((j1 + 1) * 5 + k1 + 0) * 17 + l1 + 1] - d3) * 0.125D;
                    double d8 = (this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 1] - d4) * 0.125D;

                    for (int i2 = 0; i2 < 8; ++i2)
                    {
                        double d9 = 0.25D;
                        double d10 = d1;
                        double d11 = d2;
                        double d12 = (d3 - d1) * 0.25D;
                        double d13 = (d4 - d2) * 0.25D;

                        for (int j2 = 0; j2 < 4; ++j2)
                        {
                            double d14 = 0.25D;
                            double d15 = d10;
                            double d16 = (d11 - d10) * 0.25D;

                            for (int k2 = 0; k2 < 4; ++k2)
                            {
                                IBlockState iblockstate = null;

                                if (l1 * 8 + i2 < j)
                                {
                                    iblockstate = YOUR_LIQUID;
                                }

                                if (d15 > 0.0D)
                                {
                                    iblockstate = MAIN_BLOCK;
                                }

                                //This has omthing to do with how mnay cubes spawn 
                                //Really not sure what this does
                                int l2 = j2 + j1 /2;
                                //This is some exponential with how Hight the cubes spawn
                                int i3 = i2 + l1 * 6;
                                //This either i figuerd is twas something with x,z but idk
                                int j3 = k2 + k1 /2;
                                primer.setBlockState(l2, i3, j3, iblockstate);
                                d15 += d16;
                            }

                            d10 += d12;
                            d11 += d13;
                        }

                        d1 += d5;
                        d2 += d6;
                        d3 += d7;
                        d4 += d8;
                        
                        
                        
                    }
                    
                    
                }
            }
        }
        
        
    }

    public void buildSurfaces(int x, int z, ChunkPrimer primer)
    {
        if (!ForgeEventFactory.onReplaceBiomeBlocks(this, x, z, primer, this.world)) return;
        int i = this.world.getSeaLevel() + 1;
        double d0 = 0.03125D;
        this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, x * 16, z * 16, 0, 16, 16, 1, 0.03125D, 0.03125D, 1.0D);
        this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, x * 16, 109, z * 16, 16, 1, 16, 0.03125D, 1.0D, 0.03125D);
        this.depthBuffer = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.depthBuffer, x * 16, z * 16, 0, 16, 16, 1, 0.0625D, 0.0625D, 0.0625D);

        for (int j = 0; j < 16; ++j)
        {
            for (int k = 0; k < 16; ++k)
            {
                boolean flag = this.slowsandNoise[j + k * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                boolean flag1 = this.gravelNoise[j + k * 16] + this.rand.nextDouble() * 0.2D > 0.0D;
                int l = (int)(this.depthBuffer[j + k * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
                int i1 = -1;
                IBlockState iblockstate = MAIN_BLOCK;
                IBlockState iblockstate1 = MAIN_BLOCK;

                for (int j1 = 127; j1 >= 0; --j1)
                {
                    if (j1 < 127 - this.rand.nextInt(5) && j1 > this.rand.nextInt(5))
                    {
                        IBlockState iblockstate2 = primer.getBlockState(k, j1, j);

                        if (iblockstate2.getBlock() != null && iblockstate2.getMaterial() != Material.AIR)
                        {
                            if (iblockstate2.getBlock() == MAIN_BLOCK.getBlock())
                            {
                                if (i1 == -1)
                                {
                                    if (l <= 0)
                                    {
                                        iblockstate = AIR;
                                        iblockstate1 = MAIN_BLOCK;
                                    }
                                    else if (j1 >= i - 4 && j1 <= i + 1)
                                    {
                                        iblockstate = MAIN_BLOCK;
                                        iblockstate1 = MAIN_BLOCK;

                                        if (flag1)
                                        {
                                            iblockstate = OTHER_BLOCK1;
                                            iblockstate1 = MAIN_BLOCK;
                                        }

                                        if (flag)
                                        {
                                            iblockstate = OTHER_BLOCK2;
                                            iblockstate1 = OTHER_BLOCK2;
                                        }
                                    }

                                    if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
                                    {
                                        iblockstate = YOUR_LIQUID;
                                    }

                                    i1 = l;

                                    if (j1 >= i - 1)
                                    {
                                        primer.setBlockState(k, j1, j, iblockstate);
                                    }
                                    else
                                    {
                                        primer.setBlockState(k, j1, j, iblockstate1);
                                    }
                                }
                                else if (i1 > 0)
                                {
                                    --i1;
                                    primer.setBlockState(k, j1, j, iblockstate1);
                                }
                            }
                        }
                        else
                        {
                            i1 = -1;
                        }
                    }
                    else
                    {
                        primer.setBlockState(k, j1, j, BEDROCK);
                    }
                }
            }
        }
    }

    @Override
    public Chunk generateChunk(int x, int z)
    {
        this.rand.setSeed((long)x * 341873128712L + (long)z * 132897987541L);
        ChunkPrimer chunkprimer = new ChunkPrimer();
        this.prepareHeights(x, z, chunkprimer);
        this.buildSurfaces(x, z, chunkprimer);
        
        //Keep this - This makes all the holes and stuff in the nether - IT IS NEEDED!
        this.genNetherCaves.generate(this.world, x, z, chunkprimer);

        if (this.generateStructures)
        {
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        byte[] abyte = chunk.getBiomeArray();

        for (int i = 0; i < abyte.length; ++i)
        {
            abyte[i] = (byte)Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND);
        }

        chunk.resetRelightChecks();
        return chunk;
    }

    private double[] getHeights(double[] noiseField, int x, int y, int z, int xSize, int ySize, int zSize)
    {
        if (noiseField == null) noiseField = new double[xSize * ySize * zSize];

        InitNoiseField event = new InitNoiseField(this, noiseField, x, y, z, xSize, ySize, zSize);
        MinecraftForge.EVENT_BUS.post(event);
        if (event.getResult() == Event.Result.DENY) return event.getNoisefield();

        double d0 = 684.412D;
        double d1 = 2053.236D;
        this.noiseData4 = this.scaleNoise.generateNoiseOctaves(this.noiseData4, x, y, z, xSize, 1, zSize, 1.0D, 0.0D, 1.0D);
        this.dr = this.depthNoise.generateNoiseOctaves(this.dr, x, y, z, xSize, 1, zSize, 100.0D, 0.0D, 100.0D);
        this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, x, y, z, xSize, ySize, zSize, 8.555150000000001D, 34.2206D, 8.555150000000001D);
        this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, x, y, z, xSize, ySize, zSize, 684.412D, 2053.236D, 684.412D);
        this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, x, y, z, xSize, ySize, zSize, 684.412D, 2053.236D, 684.412D);
        int i = 0;
        double[] adouble = new double[ySize];

        for (int j = 0; j < ySize; ++j)
        {
            adouble[j] = Math.cos((double)j * Math.PI * 6.0D / (double)ySize) * 2.0D;
            double d2 = (double)j;

            if (j > ySize / 2)
            {
                d2 = (double)(ySize - 1 - j);
            }

            if (d2 < 4.0D)
            {
                d2 = 4.0D - d2;
                adouble[j] -= d2 * d2 * d2 * 10.0D;
            }
        }

        for (int l = 0; l < xSize; ++l)
        {
            for (int i1 = 0; i1 < zSize; ++i1)
            {
                double d3 = 0.0D;

                for (int k = 0; k < ySize; ++k)
                {
                    double d4 = adouble[k];
                    double d5 = this.ar[i] / 512.0D;
                    double d6 = this.br[i] / 512.0D;
                    double d7 = (this.pnr[i] / 10.0D + 1.0D) / 2.0D;
                    double d8;

                    if (d7 < 0.0D)
                    {
                        d8 = d5;
                    }
                    else if (d7 > 1.0D)
                    {
                        d8 = d6;
                    }
                    else
                    {
                        d8 = d5 + (d6 - d5) * d7;
                    }

                    d8 = d8 - d4;

                    if (k > ySize - 4)
                    {
                        double d9 = (double)((float)(k - (ySize - 4)) / 3.0F);
                        d8 = d8 * (1.0D - d9) + -10.0D * d9;
                    }

                    if ((double)k < 0.0D)
                    {
                        double d10 = (0.0D - (double)k) / 4.0D;
                        d10 = MathHelper.clamp(d10, 0.0D, 1.0D);
                        d8 = d8 * (1.0D - d10) + -10.0D * d10;
                    }

                    noiseField[i] = d8;
                    ++i;
                }
            }
        }

        return noiseField;
    }

    @SuppressWarnings("deprecation")
	@Override
    public void populate(int x, int z)
    {
        BlockFalling.fallInstantly = true;
        ForgeEventFactory.onChunkPopulate(true, this, this.world, this.rand, x, z, false);
        int i = x * 16;
        int j = z * 16;
        BlockPos blockpos = new BlockPos(i, 0, j);
        Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
        ChunkPos chunkpos = new ChunkPos(x, z);

        //This and any other things you may wish to add
        if (TerrainGen.populate(this, this.world, this.rand, x, z, false, PopulateChunkEvent.Populate.EventType.NETHER_LAVA))
        {
            for (int k = 0; k < 8; ++k) { this.hellSpringGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8)); }
        }
        
        if(generateStructures)
        {
        }

        ForgeEventFactory.onChunkPopulate(false, this, this.world, this.rand, x, z, false);
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(this.world, this.rand, blockpos));
        biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(this.world, this.rand, blockpos));
        BlockFalling.fallInstantly = false;
    }

    public boolean generateStructures(Chunk chunkIn, int x, int z)
    {
        return true;
    }

    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos)
    {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored)
    {
    	return null;
    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos)
    {
        return false;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z)
    {
    }
}
