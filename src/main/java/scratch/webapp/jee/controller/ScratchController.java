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
 * A simple controller with a request mapping to the the path "/scratch.
 *
 * @author Karl Bennett
 */
@Path("scratch")
public class ScratchController {

    /**
     * This is the JPA entity manager that will be used to persist the {@link User} objects. Currently using it
     * directly, but will move it into a data resource object and then try to make the {@link User} class a proper
     * domain object.
     */
    @PersistenceContext(unitName = "users-pu")
    private EntityManager entityManager;
    /**
     * This is the containers transaction manager that is required to create transactions for data source writes.
     */
    @Resource
    private UserTransaction transaction;


    /**
     * Map any get request to the "/scratch" URI to this method.
     *
     * @return the {@code String} "scratched".
     */
    @GET
    @Produces("text/plain")
    public String handle() {

        return "scratched";
    }

    /**
     * Persist a new user using the user object that has been deserialised from the {@code JSON} in the body of the
     * {@code POST} request.
     * <p/>
     * This operation will fail if a user exists with the emil supplied in the new user. Also if an ID is supplied it
     * will be ignored unless it is an ID for an existing user at which point the persistence will fail.
     *
     * @param user the user to persist. This is automatically deserialised from the JSON in the body of the request.
     * @return the persisted user.
     * @throws SystemException            if there is an internal server error.
     * @throws NotSupportedException      if persistence isn't supported.
     * @throws HeuristicRollbackException if the commit fails.
     * @throws HeuristicMixedException    if the commit fails.
     * @throws RollbackException          if the commit fails.
     */
    @POST
    @Path("users")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public User create(@Valid final User user) throws SystemException, NotSupportedException,
            HeuristicRollbackException, HeuristicMixedException, RollbackException {

        transaction.begin();
        entityManager.persist(user);
        transaction.commit();

        return user;
    }

    /**
     * Retrieve the user with the supplied ID.
     *
     * @param id the id of the required {@code User}.
     * @return the requested {@code User}.
     */
    @GET
    @Path("users/{id}")
    @Produces(APPLICATION_JSON)
    public User retrieve(@PathParam("id") Long id) {

        return entityManager.find(User.class, id);
    }

    /**
     * Retrieve all the persisted user.
     *
     * @return all the users that have been persisted.
     */
    @GET
    @Path("users")
    @Produces(APPLICATION_JSON)
    public Iterable<User> retrieveAll() {

        CriteriaQuery<User> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(User.class);

        criteriaQuery.select(criteriaQuery.from(User.class));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Updated the user that has been deserialised from the {@code JSON} in the body of the {@code PUT} request.
     *
     * @param id   the ID of the user to update.
     * @param user the deserialised user minus the ID.
     * @return the updated user.
     * @throws SystemException            if there is an internal server error.
     * @throws NotSupportedException      if persistence isn't supported.
     * @throws HeuristicRollbackException if the commit fails.
     * @throws HeuristicMixedException    if the commit fails.
     * @throws RollbackException          if the commit fails.
     */
    @PUT
    @Path("users/{id}")
    @Consumes(APPLICATION_JSON)
    @Produces(APPLICATION_JSON)
    public User update(@PathParam("id") Long id, @Valid User user) throws SystemException, NotSupportedException,
            HeuristicRollbackException, HeuristicMixedException, RollbackException {

        user.setId(id);

        transaction.begin();
        entityManager.merge(user);
        transaction.commit();

        return user;
    }

    /**
     * Delete the user with the supplied ID.
     *
     * @param id the ID of the user to delete.
     * @return the delete user.
     * @throws SystemException            if there is an internal server error.
     * @throws NotSupportedException      if persistence isn't supported.
     * @throws HeuristicRollbackException if the commit fails.
     * @throws HeuristicMixedException    if the commit fails.
     * @throws RollbackException          if the commit fails.
     */
    @DELETE
    @Path("users/{id}")
    @Produces(APPLICATION_JSON)
    public User delete(@PathParam("id") Long id) throws SystemException, NotSupportedException,
            HeuristicRollbackException, HeuristicMixedException, RollbackException {

        transaction.begin();
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
        transaction.commit();

        return user;
    }
}
