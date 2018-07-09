package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import views.html.platforms;
import models.*;

import java.util.List;

public class PlatformsController extends Controller {

    public Result platforms() {
        List<Platform> platformList = Platform.findAllPlatforms();
        return ok(platforms.render(platformList));
    }

    public Result createNewPlatform() {
        return TODO;
    }

    public Result showSelectedPlatform(String platformID) {
        return TODO;
    }

    public Result savePlatform() {
        return TODO;
    }

}
