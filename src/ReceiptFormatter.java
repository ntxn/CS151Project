/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author sarahmai
 */
public interface ReceiptFormatter
{
    String formatHeader();
    String formatFooter();
    String formatReservation(Reservation r);
}
