public enum DamageRelationType {
    DOUBLE_DAMAGE_TO {
        @Override
        public String toString() {
            return "double_damage_to";
        }

        @Override
        public double getRatio() {
            return 2.0;
        }

    }, HALF_DAMAGE_TO {
        @Override
        public String toString() {
            return "half_damage_to";
        }

        @Override
        public double getRatio() {
            return 0.5;
        }

    }, NO_DAMAGE_TO {
        @Override
        public String toString() {
            return "no_damage_to";
        }

        @Override
        public double getRatio() {
            return 0.0;
        }

    };

    public abstract double getRatio();
}
