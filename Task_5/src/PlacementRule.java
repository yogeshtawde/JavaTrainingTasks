@FunctionalInterface
public interface PlacementRule
{
    boolean validate(Student student);

    default PlacementRule and(PlacementRule other)
    {
        return student -> this.validate(student) && other.validate(student);
    }

    default PlacementRule or(PlacementRule other)
    {
        return student -> this.validate(student) || other.validate(student);
    }

    default PlacementRule negate()
    {
        return student -> !this.validate(student);
    }
}