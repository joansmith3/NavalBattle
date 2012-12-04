package com.jpii.navalbattle.game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.jpii.navalbattle.pavo.Chunk;
import com.jpii.navalbattle.pavo.WorldGen;

public class World {
	WorldGen gen;
	Chunk[] chunks;
	BufferedImage buffer;
	boolean needsNewRender = false;
	public World() {
		chunks = new Chunk[65];
		for (int c = 0; c < chunks.length; c++) {
			chunks[c] = new Chunk();
		}
	}
	public void setWorldGen(WorldGen wg) {
		gen = wg;
	}
	public WorldGen getWorldGen() {
		return gen;
	}
	public boolean hasMoreChunks() {
		for (int c = 0; c < chunks.length; c++) {
			while (chunks[c].isLocked()) {
				
			}
			chunks[c].lock();
			if (!chunks[c].isGenerated()) {
				chunks[c].unlock();
				return true;
			}
			chunks[c].unlock();
		}
		return false;
	}
	public void genNextChunk() {
		for (int c = 0; c < chunks.length; c++) {
			Chunk chunk = chunks[c];
			while (chunk.isLocked()) {
				
			}
			chunk.lock();
			if (!chunk.isGenerated()){
				//System.out.println("Chunk at " + c + " generated.");
				chunk.render();
				needsNewRender = true;
				//break;
			}
			chunk.unlock();
			chunks[c] = chunk;
		}
	}
	public void render() {
		//if (!needsNewRender)
			//return;
		buffer = new BufferedImage(800,600,BufferedImage.TYPE_INT_RGB);
		Graphics g = buffer.getGraphics();
		for (int c = 0; c < chunks.length; c++) {
		//for (int x = 0; x < 8; x++) {
			//for (int z = 0; z < 8; z++) {
			Chunk chunk = chunks[c];
			while (chunk.isLocked())
			{
				
			}
			chunk.lock();
				g.drawImage(chunk.getBuffer(), c*100,0,null);
				chunk.unlock();
			//}
		}
		needsNewRender = false;
	}
	public BufferedImage getBuffer() {
		return buffer;
	}
}
