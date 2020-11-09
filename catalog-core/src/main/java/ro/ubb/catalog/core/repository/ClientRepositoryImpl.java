package ro.ubb.catalog.core.repository;

import ro.ubb.catalog.core.model.Client;
import ro.ubb.catalog.core.model.Movie;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class ClientRepositoryImpl extends CustomRepositorySupport implements ClientRepositoryCustom {
    @Override
    public List<Client> findAllWithRentalsAndMovieJPQL() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select distinct c from Client c" +
                "left join fetch c.rentals r " +
                "left join fetch r.movie");
        List<Client> clients = query.getResultList();

        return clients;
    }

  /*  @Override
    public List<Movie> findAllWithRentalsAndClientCriteriaAPI() {
        EntityManager entityManager = getEntityManager();

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Movie> query = criteriaBuilder.createQuery(Movie.class);
        query.distinct(Boolean.TRUE);
        Root<Movie> root = query.from(Movie.class);
        Fetch<Movie, Rental> movieRentalFetch = root.fetch(Movie.rentals);
        authorBookFetch.fetch(Book_.publisher);

        List<Author> authors = entityManager.createQuery(query).getResultList();

        //return null;
    }
    */
   /* @Override
    @Transactional
    public List<Movie> findAllWithRentalsAndClientSQL() {
        HibernateEntityManager hibernateEntityManager = getEntityManager().unwrap(HibernateEntityManager.class);
        Session session = hibernateEntityManager.getSession();

        org.hibernate.Query query = session.createSQLQuery("select distinct {m.*},{r.*},{cl.*} " +
                "from Movie m " +
                "left join Rental r on m.id=r.movie_id " +
                "left join Client cl on r.client_id=cl.id ")
                .addEntity("m", Movie.class)
                .addJoin("r", "m.rentals")
                .addJoin("cl", "r.client")
                .addEntity("m", Movie.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Movie> movies = query.getResultList();


        return movies;
    }*/
}
