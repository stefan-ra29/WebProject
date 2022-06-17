package repository;

public class LogicalEntity<Entity> {
    public LogicalEntity(Entity entity) {
        this.entity = entity;
        this.deleted = false;
    }
    public Entity entity;
    public boolean deleted;
}
