package com.violin.glsurfaceview;

import org.cocos2d.actions.instant.CCCallFunc;
import org.cocos2d.actions.instant.CCHide;
import org.cocos2d.actions.instant.CCPlace;
import org.cocos2d.actions.interval.CCDelayTime;
import org.cocos2d.actions.interval.CCSequence;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGSize;

/**
 * Created by wanghuilin on 2018/3/26.
 * <p>
 * email:violinlin@yeah.net
 */

public class SplashLayer extends CCLayer{
    protected CGSize cgSize ;
    protected static SoundEngine engine;
    static{
        engine = SoundEngine.sharedEngine();
    }
    public SplashLayer() {
        cgSize = CCDirector.sharedDirector().getWinSize();
        splash();
    }

    private void splash() {
        // TODO Auto-generated method stub
        CCSprite loading = CCSprite.sprite("background.png");
        loading.setPosition(cgSize.width/2,cgSize.height/2);//居中显示
        this.addChild(loading);
        //显示1s，0.5s后删除
        CCSequence ccSequence = CCSequence.actions( CCDelayTime.action(1), CCHide.action(),CCDelayTime.action(0.5F), CCCallFunc.action(this, "login"));
        loading.runAction(ccSequence);
    }
}
