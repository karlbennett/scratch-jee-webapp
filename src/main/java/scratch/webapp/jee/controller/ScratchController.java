package scratch.webapp.jee.controller;

import scratch.webapp.jee.data.User;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.*;
import javax.validation.Valid;
import javax.ws.rs.*;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

/**
 * @author Karl Bennett
 */
@Path("/scratch")
public class ScratchController {

    @PersistenceContext(unitName = "users-pu")
    private EntityManager entityManager;
    @Resource
    private UserTransaction transaction;

    @GET
    @Produces("text/plain")
    public String handle() {
        return "scratched";
    }

    @POST
    @Path("/users")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public User create(@Valid final User user) throws SystemException, NotSupportedException,
            HeuristicRollbackException, HeuristicMixedException, RollbackException {

        transaction.begin();
        entityManager.persist(user);
        transaction.commit();

        return user;
    }

    @GET
    @Path("/users")
    @Produces(APPLICATION_JSON)
    public Iterable<User> retrieveAll() {

        CriteriaQuery<User> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(User.class);

        criteriaQuery.select(criteriaQuery.from(User.class));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
