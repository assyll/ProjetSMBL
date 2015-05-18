package convertNeo4jToGraphStream;

import org.neo4j.graphdb.RelationshipType;

public enum Neo4JRelationshipType implements RelationshipType {
	UNDIRECTED, DIRECTED
}
