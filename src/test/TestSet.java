//package test;
//
//import java.awt.Robot;
//import java.awt.event.KeyEvent;
//
//class TestSet (){
//
//    Robot robot = new Robot();
//
//    public void TestWithSequeces() {
//
//        int[][] input_sequences = {
//          {KeyEvent.VK_1, KeyEvent.VK_1, KeyEvent.VK_1, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7},
//          {KeyEvent.VK_1, KeyEvent.VK_1, KeyEvent.VK_1, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7},
//          {KeyEvent.VK_1, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7},
//          {KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_2, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7},           {KeyEvent.VK_2, KeyEvent.VK_2, KeyEvent.VK_2, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7}
//        };
//
//        for (int sequence_counter = 0; sequence_counter < input_sequences.length; sequence_counter++) {
//
//            for (int element_counter = 0; element_counter < input_sequences[element_counter].length; element_counter++) {
//                robot.keyPress(input_sequences[sequence_counter][element_counter]);
//                robot.keyPress(KeyEvent.VK_ENTER);
//            }
//        }
//    }
//}
