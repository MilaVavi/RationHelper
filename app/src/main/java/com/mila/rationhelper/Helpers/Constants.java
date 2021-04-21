package com.mila.rationhelper.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.mila.rationhelper.Database.RecipeEntity;
import com.mila.rationhelper.Database.SugarRecordEntity;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class Constants {
    public static final String EXTRA_RECIPE_ENTITY = "extra recipe entity";
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    public static final String [] GENDERS = {"Male", "Female"};
    public static final int DEFAULT_GENDER = 0;
    public static final String [] PHYSICAL_ACTIVITIES = {"Light", "Moderate", "Active"};
    public static final int DEFAULT_ACTIVITY_LEVEL = 0;

    public static final int DEFAULT_HEIGHT = -1;
    public static final int DEFAULT_AGE = -1;
    public static final int DEFAULT_WEIGHT = -1;

    public static final String PREFERENCES_GENDER = "gender";
    public static final String PREFERENCES_ACTIVITY = "activity";
    public static final String PREFERENCES_HEIGHT = "height";
    public static final String PREFERENCES_AGE = "age";
    public static final String PREFERENCES_WEIGHT = "weight";


    public static final String [] RECIPE_CATEGORIES = {"salad and veges", "main dishes", "fruits and deserts", "drinks", "other"};

    public static final String [] MEALS = {"breakfast", "lunch", "dinner"};
    public static final String EXTRA_MEAL = "extra meal type";

    public static final String EXTRA_RECIPE_SOURCE = "extra recipe source";
    public static final String [] RECIPE_SOURCES = {"local", "remote"};

    public static final String LOCAL_BUTTON_TEXT = "Remove from menu";
    public static final String REMOTE_BUTTON_TEXT = "Add to menu";

    public static final String SHARED_PREFERENCES_NAME = "personalDetails";

    public static int measurmentsChoseBG(double level){
        if(level > 10)
            return Color.parseColor("#f2a0a3");
        if (level > 7)
            return Color.parseColor("#f7dcd9");
        if (level > 3.9)
            return Color.parseColor("#a3eaa3");
        if (level > 3)
            return Color.parseColor("#ddea93");
        else
            return Color.parseColor("#e7c5c5");
    }


    @NotNull
    public static String calculateCalories(@NotNull Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        int calories = 1500;

        int genderSelection = sharedPreferences.getInt(PREFERENCES_GENDER, DEFAULT_GENDER);
        int activitySelection = sharedPreferences.getInt(PREFERENCES_ACTIVITY, DEFAULT_ACTIVITY_LEVEL);
        int height = sharedPreferences.getInt(PREFERENCES_HEIGHT, DEFAULT_HEIGHT);
        int age = sharedPreferences.getInt(PREFERENCES_AGE, DEFAULT_AGE);
        int weight = sharedPreferences.getInt(PREFERENCES_WEIGHT, DEFAULT_WEIGHT);

        if (height != DEFAULT_HEIGHT && age != DEFAULT_AGE && weight != DEFAULT_WEIGHT){
            int genderDif ;
            if (genderSelection==0)
                genderDif=5;
            else
                genderDif=-161;

            double preBMR = 10*weight + 6.25 * height - 5 * age + genderDif;
            double activityIndex;
            switch (activitySelection){
                case 0:
                    activityIndex= 1.53;
                    break;
                case 1:
                    activityIndex = 1.83;
                    break;
                case 2:
                    activityIndex = 2.13;
                    break;
                default:
                    activityIndex = 1.83;
            }

            calories = (int) (preBMR * activityIndex);
        }
        return calories +" kcal";
    }











    // SAMPLE DATA
    @NotNull
    public static RecipeEntity[] getSampleRecipes(){
        RecipeEntity[] recipes = new RecipeEntity[4];

        // dummy objects
        ArrayList<String> recipe1ArrayList = new ArrayList<>();
        recipe1ArrayList.add("pick the apple");
        recipe1ArrayList.add("eat the apple");
        ArrayList<String> ingredients1 = new ArrayList<>();
        ingredients1.add("apple");
        RecipeEntity dummyRecipe1 = new RecipeEntity("Aple", 100, recipe1ArrayList, ingredients1, "T", "F", "F", RECIPE_CATEGORIES[2], "https://www.caloriesecrets.net/wp-content/uploads/2017/03/apple-seeds.jpg");
        recipes[0] = dummyRecipe1;

        ArrayList<String> recipe2ArrayList = new ArrayList<>();
        recipe2ArrayList.add("heat the frying pan");
        recipe2ArrayList.add("pour the thing");
        recipe2ArrayList.add("wait till ready");
        ArrayList<String> ingredients2 = new ArrayList<>();
        ingredients2.add("flour");
        ingredients2.add("pancakes things");
        RecipeEntity dummyRecipe2 = new RecipeEntity("Pancakes", 500, recipe2ArrayList, ingredients2, "F", "T", "F", RECIPE_CATEGORIES[1], "https://www.graceandgoodeats.com/wp-content/uploads/2015/01/best-ever-homemade-pancakes-500x375.jpg");
        recipes[1] = dummyRecipe2;

        ArrayList<String> recipe3ArrayList = new ArrayList<>();
        recipe3ArrayList.add("open Edie's rockets webpage");
        recipe3ArrayList.add("order burger");
        ArrayList<String> ingredients3 = new ArrayList<>();
        ingredients3.add("access to the internet");
        RecipeEntity dummyRecipe3 = new RecipeEntity("Burger", 5500, recipe3ArrayList, ingredients3, "F", "F", "T", RECIPE_CATEGORIES[1], "https://i.pinimg.com/originals/0a/fa/9e/0afa9e53ae26dfef726e71cd20491bd3.jpg");
        recipes[2] = dummyRecipe3;

        ArrayList<String> recipe4ArrayList = new ArrayList<>();
        recipe4ArrayList.add("pick the fruit");
        recipe4ArrayList.add("squeeze hard enough");
        recipe4ArrayList.add("drink");
        ArrayList<String> ingredients4 = new ArrayList<>();
        ingredients4.add("fruit");
        RecipeEntity dummyRecipe4 = new RecipeEntity("Juice", 150, recipe4ArrayList, ingredients4, "F", "F", "T", RECIPE_CATEGORIES[3], "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/juices-long-d4d60df.jpg?quality=90&resize=960,872");
        recipes[3] = dummyRecipe4;

        return recipes;
    }

    public static RecipeEntity[] getSampleRecipesFirebase(){
        // TODO replace with recipes for a firebase
        RecipeEntity[] recipes = new RecipeEntity[18];

        // dummy objects
        ArrayList<String> recipe1ArrayList = new ArrayList<>();
        recipe1ArrayList.add("Put everything in a saucepan (non-stick if you have it) and gradually bring to the boil. Do stand beside it and watch it as it can easily boil over.");
        recipe1ArrayList.add("Once it has come to the boil turn it down and simmer for 5 to 10 minutes depending on how tender you like your oats");
        recipe1ArrayList.add("Stir it occasionally. If it gets too thick (and how thick you like it is up to you) just add an extra drop of milk or water, but remember adding extra milk will add to the total carbohydrate content of the breakfast.");
        recipe1ArrayList.add("If you want to microwave the porridge, just follow the instructions on the pack");
        ArrayList<String> ingredients1 = new ArrayList<>();
        ingredients1.add("for 1 portion");
        ingredients1.add("½ cup (or ¼ of a mug) of dry porridge oats");
        ingredients1.add("1 cup (or ½ mug) of low fat milk");
        ingredients1.add("1 teaspoon of honey");
        RecipeEntity fbRecipe1 = new RecipeEntity("Traditional porridge",432,recipe1ArrayList, ingredients1, "T", "T","T",RECIPE_CATEGORIES[1], "https://www.diabetes.ie/wp-content/uploads/2014/11/Porridgepic.jpg");
        recipes[0] = fbRecipe1;

        ArrayList<String>recipe2ArrayList = new ArrayList<>();
        recipe2ArrayList.add("Slice the tomato and aubergine into slices ½ inch thick. ");
        recipe2ArrayList.add("You want 1-2 slices of tomato and 1-2 slices of aubergine per bagel.");
        recipe2ArrayList.add("Brush the slices with a little of olive oil, put them on a baking tray and roast them in the oven at 200 oC for 5 minutes, turning once.");
        recipe2ArrayList.add("Put the 2 eggs on to poach.");
        recipe2ArrayList.add("Spread the bagels with about 1 dessertspoon of basil pesto each.");
        recipe2ArrayList.add("Toast them under the grill until the edges start to brown.");
        recipe2ArrayList.add("Top the bagels with slices of aubergine and tomato and sit an egg on top. Enjoy!");
        ArrayList<String> ingredients2 = new ArrayList<>();
        ingredients2.add("for 2 portions");
        ingredients2.add("2 bagels (you can try different types of breads)");
        ingredients2.add("1 jar basil pesto");
        ingredients2.add("1 large tomato (beef tomatoes if you can get them)");
        ingredients2.add("¼ aubergine");
        ingredients2.add("2 eggs");
        ingredients2.add("Salt and pepper to taste");
        RecipeEntity fbRecipe2 = new RecipeEntity("Veggie-topped bagel with poached egg", 361, recipe2ArrayList, ingredients2, "T", "T", "T", RECIPE_CATEGORIES[1], "https://www.diabetes.ie/wp-content/uploads/2014/11/veggy-topped-bagel.jpg");
        recipes[1] = fbRecipe2;

        ArrayList<String> recipe3ArrayList = new ArrayList<>();
        recipe3ArrayList.add("Mix all the ingredients together and store in an airtight container in a dark cupboard.");
        recipe3ArrayList.add("Serve with milk or topped with yogurt and fresh fruit");
        recipe3ArrayList.add("Makes 10 portions.");
        ArrayList<String> ingredients3 = new ArrayList<>();
        ingredients3.add("for 10 portions ");
        ingredients3.add("1 pack Muesli Cereal Base (or a mixture of rolled wheat and oats available at most supermarkets) ");
        ingredients3.add("100g mixed dried fruit – try blueberries, cranberries, raisins and chopped apricots");
        ingredients3.add("50g sesame seeds");
        ingredients3.add("50g sunflower seeds");
        ingredients3.add("100g chopped mixed nuts (e.g. brazil nuts, hazelnuts, walnuts and almonds)");
        RecipeEntity fbRecipe3 = new RecipeEntity("HOMEMADE FRUITY NUTTY MUESLI", 220, recipe3ArrayList, ingredients3, "T", "T", "T", RECIPE_CATEGORIES[4], "https://www.diabetes.ie/wp-content/uploads/2014/11/muesli.jpg");
        recipes[2]= fbRecipe3;

        ArrayList<String> recipe4ArrayList = new ArrayList<>();
        recipe4ArrayList.add("Mix everything together and eat immediately.");
        ArrayList<String> ingredients4 = new ArrayList<>();
        ingredients4.add("for 1 portion");
        ingredients4.add("1 small pot of natural yogurt (125g)");
        ingredients4.add("3 tablespoons of fresh blueberries");
        ingredients4.add("2 tablespoons of muesli");
        RecipeEntity fbRecipe4 = new RecipeEntity("CRUNCHY BLUEBERRY YOGHURT", 218, recipe4ArrayList, ingredients4, "T", "T","T",RECIPE_CATEGORIES [2], "https://www.diabetes.ie/wp-content/uploads/2014/11/BerryYougurt240x185.jpg");
        recipes[3] = fbRecipe4;

        ArrayList<String> recipe5ArrayList = new ArrayList<>();
        recipe5ArrayList.add("Make sure all the ingredients are chilled before use");
        recipe5ArrayList.add("Wash and peel fruit");
        recipe5ArrayList.add("Blend fruit, yogurt, milk and juice together using a hand held blender or a smoothie maker until creamy");
        recipe5ArrayList.add("Add ice cubes and blend again");
        recipe5ArrayList.add("Pour into two glasses and serve");
        ArrayList<String> ingredients5 = new ArrayList<>();
        ingredients5.add("for 2 portions");
        ingredients5.add("160g fresh pineapple");
        ingredients5.add("½ pot (62g) of low fat natural yogurt");
        ingredients5.add("90ml low fat milk");
        ingredients5.add("4 ice cubes");
        ingredients5.add("90ml unsweetened orange juice");
        RecipeEntity fbRecipe5 =new RecipeEntity("SIMPLE PINEAPPLE SMOOTHIE", 89, recipe5ArrayList,ingredients5,"T","T","T", RECIPE_CATEGORIES[3], "https://www.diabetes.ie/wp-content/uploads/2014/11/banana-oaty-smoothie.jpg");
        recipes[4] = fbRecipe5;

        ArrayList<String> recipe6ArrayList = new ArrayList<>();
        recipe6ArrayList.add("Get a large salad bowl and add the chopped chicken");
        recipe6ArrayList.add("Break the broccoli into bite size florets and add to the bowl with the sliced celery, chopped apple and finely sliced spring onions or scallions");
        recipe6ArrayList.add("Sprinkle over the fresh herbs and pour over 1 dessertspoon of French salad dressing");
        ArrayList<String> ingredients6 = new ArrayList<>();
        ingredients6.add("for 1 portion");
        ingredients6.add("1 breast of chicken, grilled and sliced or use any leftover chicken you might have from a roast");
        ingredients6.add("1 head of broccoli");
        ingredients6.add("2 sticks of celery");
        ingredients6.add("2 spring onions or scallions");
        ingredients6.add("1 green apple, preferable Granny Smith’s");
        ingredients6.add("1 teaspoon mixed fresh thyme and rosemary, well chopped");
        ingredients6.add("1 dessertspoon of low-fat French dressing");
        RecipeEntity fbRecipe6 = new RecipeEntity("GREEN CHICKEN SALAD", 279, recipe6ArrayList, ingredients6, "T", "T", "T", RECIPE_CATEGORIES[0], "https://www.diabetes.ie/wp-content/uploads/2014/11/green-salad.jpg");
        recipes [5] = fbRecipe6;

        ArrayList<String> recipe7ArrayList = new ArrayList<>();
        recipe7ArrayList.add("Pre-heat the oven to 220°C / 425°F / Gas Mark 7");
        recipe7ArrayList.add("Wash the potatoes and then prick them all over with a fork");
        recipe7ArrayList.add("Bake in a pre-heated oven for 1½ hours or until the inside is tender");
        recipe7ArrayList.add("Heat the baked beans in a saucepan on the hob or in the microwave according to instructions on the tin");
        recipe7ArrayList.add("Cut the potato in half and carefully scoop the centre out of the potato");
        recipe7ArrayList.add("Mix this potato with the baked beans and pepper");
        recipe7ArrayList.add("Return the mixture to potato skin and sprinkle with grated cheese");
        recipe7ArrayList.add("Place in a hot oven and bake until warmed through and golden");
        ArrayList<String> ingredients7 = new ArrayList<>();
        ingredients7.add("for 2 portion");
        ingredients7.add("1 x 225g / 8 oz. small can of baked beans");
        ingredients7.add("55g / 2 oz. of low-fat cheddar cheese, grated");
        ingredients7.add("Salt and pepper to taste");
        RecipeEntity fbRecipe7 = new RecipeEntity("BEANS AND CHEESE BAKED POTATO", 276, recipe7ArrayList,ingredients7, "T", "T", "T", RECIPE_CATEGORIES [4], "https://www.diabetes.ie/wp-content/uploads/2014/11/BAKED-POTATO.jpg");
        recipes [6] = fbRecipe7;

        ArrayList<String> recipe8ArrayList = new ArrayList<>();
        recipe8ArrayList.add("Cook the chicken under a grill or non-stick pan");
        recipe8ArrayList.add("While that’s cooking, chop the avocados into small cubes");
        recipe8ArrayList.add("Mix the olive oil and pepper together with a fork");
        recipe8ArrayList.add("Remove the chicken and cut into strips");
        recipe8ArrayList.add("Serve in a salad bowl with the avocado");
        recipe8ArrayList.add("Drizzle the oil on top and garnish with the parsley according to taste");
        ArrayList<String> ingredients8 = new ArrayList<>();
        ingredients8.add("for 4 portions");
        ingredients8.add("2 ripe avocados, peeled and stones removed ");
        ingredients8.add("390g/14 oz of skinless and boneless chicken fillets, around 3 fillets");
        ingredients8.add("1 tablespoon of olive oil");
        ingredients8.add("1 handful of chopped fresh parsley");
        ingredients8.add("Freshly ground pepper to taste");
        RecipeEntity fbRecipe8 = new RecipeEntity("CHICKEN AVOCADO SALAD", 268, recipe8ArrayList, ingredients8, "T", "T", "T", RECIPE_CATEGORIES [0], "https://www.diabetes.ie/wp-content/uploads/2014/11/ChickenSalad240x90.jpg");
        recipes [7] = fbRecipe8;

        ArrayList<String> recipe9ArrayList = new ArrayList<>();
        recipe9ArrayList.add("Wash, peel and dice carrots, turnips and parsnips.");
        recipe9ArrayList.add("Wash and chop leeks, chop onion and slice mushrooms.");
        recipe9ArrayList.add("Chop the tomatoes.");
        recipe9ArrayList.add("Heat the oil in a large saucepan, and gently fry onion and mushrooms.");
        recipe9ArrayList.add("Add carrots, turnips, parsnips and leeks and fry gently.");
        recipe9ArrayList.add("Stir in the flour to absorb fat, gradually stir in the semi-skimmed milk.");
        recipe9ArrayList.add("Add stock and bring to boil, stirring continuously.");
        recipe9ArrayList.add("Add tomatoes, pepper and a pinch of salt if desired.");
        recipe9ArrayList.add("Cover saucepan and simmer gently for about 45 minutes.");
        ArrayList<String> ingredients9 = new ArrayList<>();
        ingredients9.add("for 4 portions");
        ingredients9.add("3 medium carrots");
        ingredients9.add("1 medium turnip");
        ingredients9.add("1 medium parsnip");
        ingredients9.add("2 leeks");
        ingredients9.add("1 medium onion");
        ingredients9.add("8 mushrooms");
        ingredients9.add("3 medium tomatoes");
        ingredients9.add("1 teaspoon of vegetable oil");
        ingredients9.add("50g / 2 oz. of plain flour");
        ingredients9.add("150ml / ¼ pint of low fat milk");
        ingredients9.add("2 vegetable stock cubes dissolved in 1 litre / 1¾ pints of water");
        RecipeEntity fbRecipe9 = new RecipeEntity("Farmhouse vegetable soup",151, recipe9ArrayList, ingredients9, "T","T", "T", RECIPE_CATEGORIES[4], "https://www.diabetes.ie/wp-content/uploads/2020/11/Getty_138014362-scaled.jpg");
        recipes [8] = fbRecipe9;

        ArrayList<String> recipe10ArrayList = new ArrayList<>();
        recipe10ArrayList.add("Heat the oil in a saucepan over a medium heat");
        recipe10ArrayList.add("Add the garlic and onion, cooking until they soften");
        recipe10ArrayList.add("Stir to avoid them going brown and sticking to the pan");
        recipe10ArrayList.add("Add the water, stock cube, peas, mint and parsley");
        recipe10ArrayList.add("Bring to the boil and then reduce the heat, cooking the mixture through for 8 minutes");
        recipe10ArrayList.add("Remove from the heat and leave for a few minutes until the pan cools");
        recipe10ArrayList.add("Get a food blender or processor and whiz the soup mix until smooth");
        recipe10ArrayList.add("Trim the rashers of any fat and grill them until they go crispy and crumble up");
        recipe10ArrayList.add("When the soup is ready, pour it into bowls and serve with the pepper and rashers on top");
        ArrayList<String> ingredients10 = new ArrayList<>();
        ingredients10.add("for 4 portions");
        ingredients10.add("500g / 17½ oz. of frozen green peas, defrosted first");
        ingredients10.add("2 tablespoons of olive oil");
        ingredients10.add("2 garlic cloves");
        ingredients10.add("1 medium onion");
        ingredients10.add("1 litre of water");
        ingredients10.add("2 tablespoons of chopped mint");
        ingredients10.add("2 tablespoons of chopped parsley");
        ingredients10.add("2 lean rashers, sliced thinly");
        ingredients10.add("1 vegetable stock cube");
        ingredients10.add("Fresh ground black pepper, to taste");
        ingredients10.add("1 breast of chicken, grilled and sliced or use any leftover chicken you might have from a roast");
        RecipeEntity fbRecipe10 = new RecipeEntity("Green pea soup", 167, recipe10ArrayList, ingredients10, "T", "T", "T", RECIPE_CATEGORIES[4], "https://www.diabetes.ie/wp-content/uploads/2014/11/PeaSoup.jpg" );
        recipes[9] = fbRecipe10;

        ArrayList<String> recipe11ArrayList = new ArrayList<>();
        recipe11ArrayList.add("675g skinless boneless chicken, cut into large chunks (use a mixture of breast and thigh meats)");
        recipe11ArrayList.add("1 tablespoon olive oil");
        recipe11ArrayList.add("1 medium onion, finely chopped");
        recipe11ArrayList.add("Spices –  1 level teaspoon ground ginger,  1 level teaspoon cinnamon,  1 level teaspoon ground coriander,  1 level teaspoon turmeric,  1 level teaspoon ground cumin,  pinch of chilli powder or flakes");
        recipe11ArrayList.add("1 tablespoon tomato puree");
        recipe11ArrayList.add("568ml (1 pint) chicken stock");
        recipe11ArrayList.add(" 1 small orange, zested and juiced");
        recipe11ArrayList.add("150g mixed dried fruit e.g. apricots, cranberries, raisins, sultanas");
        recipe11ArrayList.add("2 tablespoons fresh coriander, roughly chopped, to garnish");
        ArrayList<String> ingredients11 = new ArrayList<>();
        ingredients11.add("for 4 portions");
        ingredients11.add("500g / 17½ oz. of frozen green peas, defrosted first");
        ingredients11.add("2 tablespoons of olive oil");
        ingredients11.add("2 garlic cloves");
        ingredients11.add("1 medium onion");
        ingredients11.add("1 litre of water");
        ingredients11.add("2 tablespoons of chopped mint");
        ingredients11.add("2 tablespoons of chopped parsley");
        ingredients11.add("2 lean rashers, sliced thinly");
        ingredients11.add("1 vegetable stock cube");
        ingredients11.add("Fresh ground black pepper, to taste");
        ingredients11.add("1 breast of chicken, grilled and sliced or use any leftover chicken you might have from a roast");
        RecipeEntity fbRecipe11 = new RecipeEntity("Chicken & Apricot Tangine", 343, recipe11ArrayList, ingredients11, "T", "T", "T", RECIPE_CATEGORIES[1], "https://www.diabetes.ie/wp-content/uploads/2017/08/chicken-and-apricot-tagine.png" );
        recipes[10] = fbRecipe11;

        ArrayList<String> recipe12ArrayList = new ArrayList<>();
        recipe12ArrayList.add("Fry the meat in a frying pan on both sides until browned.");
        recipe12ArrayList.add("Remove from the frying pan and place in a saucepan.");
        recipe12ArrayList.add("Chop the onions and carrots and fry lightly.");
        recipe12ArrayList.add("Remove from the frying pan and place with the meat in the saucepan.");
        recipe12ArrayList.add("Sprinkle the flour into the frying pan and stir well.");
        recipe12ArrayList.add("Add the stock cubes, water and seasoning. Continue stirring.");
        recipe12ArrayList.add("Add this to the meat and vegetables and cook gently over a low heat for 1 to 1½ hours until the meat is tender.");
        ArrayList<String> ingredients12 = new ArrayList<>();
        ingredients12.add("for 4 portions");
        ingredients12.add("675g / 1½ lb of lean stewing beef, diced");
        ingredients12.add("1 dessertspoon vegetable oil");
        ingredients12.add("1 beef stock cube dissolved in 575ml / 1 pint of water or homemade stock");
        ingredients12.add("40g / 1½ oz. of plain flour");
        ingredients12.add("5 medium carrots, peeled and chopped");
        ingredients12.add("1 medium onion, chopped");
        ingredients12.add("Salt and pepper, to taste");
        RecipeEntity fbRecipe12 = new RecipeEntity(" Beef Stew", 301, recipe12ArrayList, ingredients12, "T", "T", "T", RECIPE_CATEGORIES[1], "https://www.diabetes.ie/wp-content/uploads/2021/01/Getty_176761284-scaled.jpg" );
        recipes[11] = fbRecipe12;

        ArrayList<String> recipe13ArrayList = new ArrayList<>();
        recipe13ArrayList.add("Preheat the grill for 10 minutes at a medium heat.");
        recipe13ArrayList.add("Rub a little olive oil over each salmon fillet.");
        recipe13ArrayList.add("Then add the Cajun spice to the salmon and leave to marinate in the fridge for 5 minutes.");
        recipe13ArrayList.add("Cook the salmon gently for 8 to 10 minutes, turning occasionally.");
        ArrayList<String> ingredients13 = new ArrayList<>();
        ingredients13.add("for 4 portions");
        ingredients13.add("400g / 14 oz. of salmon fillets, around 4 fillets");
        ingredients13.add("1 teaspoon of olive oil");
        ingredients13.add("1 tablespoon of Cajun spice mix");
        RecipeEntity fbRecipe13 =new RecipeEntity("Grilled Cajun Salmon", 186, recipe13ArrayList,ingredients13,"T","T","T", RECIPE_CATEGORIES[1], "https://www.diabetes.ie/wp-content/uploads/2021/01/Getty_1173526489-scaled.jpg");
        recipes[12] = fbRecipe13;

        ArrayList<String> recipe14ArrayList = new ArrayList<>();
        recipe14ArrayList.add("Pre-heat the oven to 190°C / 375°F / Gas Mark 5");
        recipe14ArrayList.add("Mix all ingredients except the chicken to make the sauce");
        recipe14ArrayList.add("Arrange the chicken in a single layer in the casserole dish");
        recipe14ArrayList.add("Spoon sauce over the chicken");
        recipe14ArrayList.add("Cover and bake for 55 to 60 minutes, until the chicken is tender and the juices run clear");
        ArrayList<String> ingredients14 = new ArrayList<>();
        ingredients14.add("for 4 portions");
        ingredients14.add("8 chicken thighs");
        ingredients14.add("2 medium onions, thinly sliced");
        ingredients14.add("1 green pepper, thinly sliced");
        ingredients14.add("6 dessertspoons of tomato sauce");
        ingredients14.add("1 dessertspoon of Worcestershire sauce");
        ingredients14.add("1 teaspoon of chilli powder");
        ingredients14.add("Salt and pepper, to taste");
        RecipeEntity fbRecipe14 = new RecipeEntity("Chicken in BBQ sauce", 344, recipe14ArrayList, ingredients14, "T", "T", "T", RECIPE_CATEGORIES[1], "https://www.diabetes.ie/wp-content/uploads/2014/11/Chickeninbarbequesauce.jpg" );
        recipes[13] = fbRecipe14;

        ArrayList<String> recipe15ArrayList = new ArrayList<>();
        recipe15ArrayList.add("Put the penne pasta in a large pan of boiling, salted water and cook for about 10 minutes until ‘al dente’ – cooked but still firm.");
        recipe15ArrayList.add("Heat a frying pan over a medium heat and add the pine nuts. Gently cook for a few minutes until lightly toasted, tossing occasionally to prevent them from burning. Tip into a bowl and set aside.");
        recipe15ArrayList.add("Add two tablespoons of oil to the frying pan and sauté the mushrooms, onion, garlic and thyme for 2 to 3 minutes until softened and just beginning to colour.");
        recipe15ArrayList.add("Stir in the mustard, turkey, creme fraiche and then bring to a gentle simmer.");
        recipe15ArrayList.add("Cook for 1 minute to just heat through but don’t allow the mixture to boil!");
        recipe15ArrayList.add("Drain the pasta and quickly refresh in cold water for a few seconds to prevent it from cooking any more, then return to the pan. Pour in the creamy turkey mixture and add the chopped rocket and basil. Toss lightly together to combine and season to taste.");
        recipe15ArrayList.add("To serve, spoon the pasta on to warmed serving plates and then sprinkle over the toasted pine nuts and garnish with some Parmesan shavings.");
        ArrayList<String> ingredients15 = new ArrayList<>();
        ingredients15.add("for 4 portions");
        ingredients15.add("200g / 8 oz. of penne pasta");
        ingredients15.add("3 tablespoons of pine nuts");
        ingredients15.add("1 tablespoon of cooking oil");
        ingredients15.add("200g / 8 oz. of cooked turkey meat, cubed");
        ingredients15.add("200g / 4 oz. of mushrooms, sliced");
        ingredients15.add("1 medium onion, thinly sliced");
        ingredients15.add("2 garlic cloves, crushed");
        ingredients15.add("1 teaspoon of fresh thyme leaves – or ½ teaspoon of dried thyme");
        ingredients15.add("1 tablespoon of fresh basil chopped – or 1 teaspoon of dried basil");
        ingredients15.add("2 teaspoons of wholegrain mustard");
        ingredients15.add("100ml / ¼ pint of low-fat crème fraiche");
        ingredients15.add("125g / 4½ oz. of wild rocket, roughly chopped");
        ingredients15.add("1 tablespoon of parmesan cheese");
        ingredients15.add("Salt and black pepper, to taste");
        RecipeEntity fbRecipe15 = new RecipeEntity("Turkey, Rocket and Pine Nut Pasta", 422, recipe15ArrayList, ingredients15, "T", "T", "T", RECIPE_CATEGORIES[1], "https://www.diabetes.ie/wp-content/uploads/2020/12/Unsplash_yfs1UJziyoM-scaled.jpg" );
        recipes[14] = fbRecipe15;

        ArrayList<String> recipe16ArrayList = new ArrayList<>();
        recipe16ArrayList.add("Heat the oil in a wok and fry the onion and garlic for 3 minutes. Add the peppers and fry for 3 minutes more.");
        recipe16ArrayList.add("Add the broccoli florets, sweet corn and courgettes and continue to fry for a further 5 minutes.");
        recipe16ArrayList.add("In a small bowl, mix together the soy sauce, light stock and ginger juice.");
        recipe16ArrayList.add("Using a wooden spoon, make a space in the centre of the stir fried vegetables so that the base of the wok is visible. Pour in the sauce and bring to the boil, stirring all the time until it starts to thicken.");
        recipe16ArrayList.add("Toss the vegetables to coat thoroughly with the sauce.");
        recipe16ArrayList.add("Transfer to a serving dish and sprinkle with the cashew nuts.");
        ArrayList<String> ingredients16 = new ArrayList<>();
        ingredients16.add("for 4 portions");
        ingredients16.add("1 tablespoon of olive oil");
        ingredients16.add("1 medium onion, peeled and sliced");
        ingredients16.add("1 clove of garlic, crushed");
        ingredients16.add("1 red pepper, sliced");
        ingredients16.add("1 yellow pepper, sliced");
        ingredients16.add("100g / 4 oz. of broccoli florets");
        ingredients16.add("175g / 6 oz. of baby sweet corn, halved");
        ingredients16.add("2 courgettes, sliced");
        ingredients16.add("2 tablespoon of light soy sauce");
        ingredients16.add("2 tablespoon of water");
        ingredients16.add("5cm / 2 inch piece of fresh root ginger, grated and juice squeezed out");
        ingredients16.add("50g / 2 oz. of unsalted cashew nuts, lightly toasted");
        RecipeEntity fbRecipe16 = new RecipeEntity("Vegetable Stir Fry", 166, recipe16ArrayList, ingredients16, "T", "T", "T", RECIPE_CATEGORIES[0], "https://www.diabetes.ie/wp-content/uploads/2014/11/StirFryVeg240x185.jpg" );
        recipes[15] = fbRecipe16;

        ArrayList<String> recipe17ArrayList = new ArrayList<>();
        recipe17ArrayList.add("Wash the apple and then cut into quarters removing the core. Cut into chunks");
        recipe17ArrayList.add("Divide the tangerines or oranges into segments");
        recipe17ArrayList.add("Wash grapes and half each one");
        recipe17ArrayList.add("Cut banana and kiwi fruit into slices");
        recipe17ArrayList.add("Cut pineapple into chunks");
        recipe17ArrayList.add("Place all fruit in the bowl, add the orange juice and mix well");
        ArrayList<String> ingredients17 = new ArrayList<>();
        ingredients17.add("for 4 portions");
        ingredients17.add("1 red or green apple");
        ingredients17.add("2 tangerines or small oranges, peeled");
        ingredients17.add("12 black or green grapes");
        ingredients17.add("1 medium banana, peeled");
        ingredients17.add("2 kiwi fruits, peeled");
        ingredients17.add("1 large thick slice of pineapple, fresh or canned");
        ingredients17.add("1 glass of fresh unsweetened orange juice – you can also use grape, pineapple or apple juice");
        RecipeEntity fbRecipe17 = new RecipeEntity("Fresh Fruit Salad", 89, recipe17ArrayList, ingredients17, "T", "T", "T", RECIPE_CATEGORIES[2], "https://www.diabetes.ie/wp-content/uploads/2014/11/FRUIT-SALAD240x185.jpg" );
        recipes[16] = fbRecipe17;

        ArrayList<String> recipe18ArrayList = new ArrayList<>();
        recipe18ArrayList.add("Sieve the flour into a large mixing bowl, make a well in the centre and then add the beaten eggs.");
        recipe18ArrayList.add("Use a balloon whisk to beat the eggs into the flour and gradually whisk in the milk until just smooth.");
        recipe18ArrayList.add("Brush a non-stick medium frying pan with the oil and when very hot pour in about 3 tablespoons worth of the batter.");
        recipe18ArrayList.add("Quickly tilt the pan from side to side to form a thick layer of batter and cook for one minute.");
        recipe18ArrayList.add("Flip the pancake over with a spatula and cook until the underside is slightly golden.");
        recipe18ArrayList.add("Continue with the rest of the batter, recoating the pan with oil when required.");
        ArrayList<String> ingredients18 = new ArrayList<>();
        ingredients18.add("for 6 portions");
        ingredients18.add("100g / 4 oz. of plain flour");
        ingredients18.add("2 medium eggs");
        ingredients18.add("300ml / 10 fl oz. of semi-skimmed milk");
        ingredients18.add("1 teaspoon of sunflower oil");
        RecipeEntity fbRecipe18 = new RecipeEntity("Pancakes", 109, recipe18ArrayList, ingredients18, "T", "T", "T", RECIPE_CATEGORIES[2], "https://www.diabetes.ie/wp-content/uploads/2021/02/Getty_504698622-scaled.jpg" );
        recipes[17] = fbRecipe18;







        // return new array recipe
        return recipes;
    }

    public static SugarRecordEntity[] getSampleRecords(){
        SugarRecordEntity[] records = new SugarRecordEntity[3];

        SugarRecordEntity dummyRecord1 = new SugarRecordEntity(7.0, Constants.DATE_FORMAT.format(new Date(System.currentTimeMillis())));
        records[0] = dummyRecord1;
        SugarRecordEntity dummyRecord2 = new SugarRecordEntity(5.0, Constants.DATE_FORMAT.format(new Date(System.currentTimeMillis())));
        records[1] = dummyRecord2;
        SugarRecordEntity dummyRecord3 = new SugarRecordEntity(9.0, Constants.DATE_FORMAT.format(new Date(System.currentTimeMillis())));
        records[2] = dummyRecord3;

        return records;
    }
}
