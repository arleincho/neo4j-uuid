package org.neo4j.extension.uuid;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.IndexHits;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;





import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.Transaction;

import java.nio.charset.Charset;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


/**
 * Simple REST webservice to find nodes/relationships by uuid
 */
@Path("/")
public class UUIDRestInterface {

    @Context
    GraphDatabaseService graphDatabaseService;
    private Transaction transaction;

    @GET
    @Path("/node/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findNodeByUUID(@PathParam("uuid") String uuid) {
        transaction = graphDatabaseService.beginTx();
        IndexHits<Node> hits = graphDatabaseService.index().forNodes(UUIDTransactionEventHandler.UUID_INDEX_NAME).get(UUIDTransactionEventHandler.UUID_PROPERTY_NAME, uuid);
        /*return Long.toString(hits.getSingle().getId());*/

        Response response = Response.status( Status.OK ).entity(Long.toString(hits.getSingle().getId())).build();
        transaction.success();
        transaction.finish();
        return response;
    }

    @GET
    @Path("/relationship/{uuid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findRelationshipByUUID(@PathParam("uuid") String uuid) {
        transaction = graphDatabaseService.beginTx();
        IndexHits<Relationship> hits = graphDatabaseService.index().forRelationships(UUIDTransactionEventHandler.UUID_INDEX_NAME).get(UUIDTransactionEventHandler.UUID_PROPERTY_NAME, uuid);
        Response response = Response.status( Status.OK ).entity(Long.toString(hits.getSingle().getId())).build();
        transaction.success();
        transaction.finish();
        return response;
        /*return Long.toString(hits.getSingle().getId());*/
    }

}
