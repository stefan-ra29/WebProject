package controller;

import beans.Membership;
import beans.User;
import com.google.gson.Gson;
import service.MembershipService;

import java.time.LocalDate;

import static spark.Spark.post;

public class MembershipController {
    private static Gson gson = new Gson();
    private static MembershipService membershipService = new MembershipService();

    public static void addMonthlyLight(){
        post("rest/memberships/addMonthlyLight", (req, res) -> {
            res.type("application/json");

            User customer = gson.fromJson(req.body(), User.class);

            String customerId = customer.getUsername();

            Membership membership = new Membership("monthlyLight", LocalDate.now(), LocalDate.now().plusMonths(1), 30, customerId, true, 30);

            membershipService.deactivatePreviousMemberships(customerId);

            return membershipService.addOne(membership);
        });

        post("rest/memberships/addMonthlyPremium", (req, res) -> {
            res.type("application/json");

            User customer = gson.fromJson(req.body(), User.class);

            String customerId = customer.getUsername();

            Membership membership = new Membership("monthlyPremium", LocalDate.now(), LocalDate.now().plusMonths(1), 45, customerId, true, 60);

            membershipService.deactivatePreviousMemberships(customerId);

            return membershipService.addOne(membership);
        });

        post("rest/memberships/addYearly", (req, res) -> {
            res.type("application/json");

            User customer = gson.fromJson(req.body(), User.class);

            String customerId = customer.getUsername();

            Membership membership = new Membership("yearly", LocalDate.now(), LocalDate.now().plusMonths(12), 180, customerId, true, 365);

            membershipService.deactivatePreviousMemberships(customerId);

            return membershipService.addOne(membership);
        });
    }
}
