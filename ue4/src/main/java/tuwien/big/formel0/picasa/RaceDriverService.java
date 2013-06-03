package tuwien.big.formel0.picasa;

import com.google.gdata.util.ServiceException;
import java.io.IOException;
import java.util.List;

import java.net.URL;

import com.google.gdata.client.*;
import com.google.gdata.client.photos.*;
import com.google.gdata.data.photos.*;
import java.util.LinkedList;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import tuwien.big.formel0.utilities.Utility;

@ManagedBean(eager = true)
@ApplicationScoped
public class RaceDriverService implements IRaceDriverService {    
    public RaceDriverService() {
            List<RaceDriver> raceDrivers;
            
            try {
                raceDrivers = getRaceDrivers();
            }
            catch(Exception e) {
                //e.printStackTrace();
                return;
            }
            
            EntityManager em = Utility.getEntityManagerFactory().createEntityManager();
            
            em.getTransaction().begin();
            for(RaceDriver rd : raceDrivers) {
                //already in db
                if(em.find(RaceDriver.class, rd.getName()) != null)
                    continue;
                
                em.persist(rd);
            }
            em.flush();
            em.getTransaction().commit();
            em.close();
    }

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

            drivers.add(driver);
        }

        return drivers;
    }
}
