package tuwien.big.formel0.picasa;

import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.util.List;

import java.io.File;
import java.net.URL;

import com.google.gdata.client.*;
import com.google.gdata.client.photos.*;
import com.google.gdata.data.*;
import com.google.gdata.data.media.*;
import com.google.gdata.data.photos.*;
import java.util.LinkedList;

public class RaceDriverService implements IRaceDriverService {

    @Override
    public List<RaceDriver> getRaceDrivers() throws IOException, ServiceException {

        PicasawebService myService = new PicasawebService("tuwien.big.formel0");

        URL albumFeedUrl = new URL("https://picasaweb.google.com/data/feed/api/user/107302466601293793664");

        Query photoQuery = new Query(albumFeedUrl);
        photoQuery.setStringCustomParameter("kind", "photo");
        photoQuery.setStringCustomParameter("tag", "Driver");

        AlbumFeed photos = myService.query(photoQuery, AlbumFeed.class);

        List<RaceDriver> drivers = new LinkedList<RaceDriver>();

        for (PhotoEntry photo : photos.getPhotoEntries()) {
            RaceDriver driver = new RaceDriver();

            // name is in the description field
            driver.setName(photo.getDescription().getPlainText());

            // change parameter for get() method to retrieve different image sizes
            driver.setUrl(photo.getMediaThumbnails().get(1).getUrl());

            // get wiki url from the corresponding tag
            for (String tag : photo.getMediaKeywords().getKeywords()) {
                if (tag.startsWith("wiki:")) {
                    driver.setWikiUrl(tag.replaceFirst("wiki:", "https://"));
                    break;
                }
            }
        }

        return drivers;
    }
}
