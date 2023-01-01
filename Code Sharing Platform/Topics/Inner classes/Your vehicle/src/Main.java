class EnjoyVehicle {
    public static void startVehicle() {
        // start your vehicle
        EnjoyVehicle v = new EnjoyVehicle();
        Engine e = v.new Engine();

        e.start();
    }

    class Engine {
        void start() {
            System.out.println("RRRrrrrrrr....");
        }
    }
}