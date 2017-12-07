package com.lexi.comp4004.game;

import java.util.Observer;

import com.lexi.comp4004.util.BroadcastKey;

public interface GameObserver extends Observer {
	public void update(GameObservable o, BroadcastKey type, Object... arg);
}
