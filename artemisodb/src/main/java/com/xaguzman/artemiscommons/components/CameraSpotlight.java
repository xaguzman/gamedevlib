package com.xaguzman.artemiscommons.components;

import com.artemis.Component;

/**
 * Makes the worldCamera to be set on the owner entity.
 *
 * NEver addA to more than 1 entity
 * Created by gdlxguzm on 4/3/2017.
 */
public class CameraSpotlight extends Component {
    public float zoom = 1;

    public boolean followX = true;
    public boolean followY = true;
}
