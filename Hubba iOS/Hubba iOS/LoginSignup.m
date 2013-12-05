//
//  LoginSignup.m
//  Hubba iOS
//
//  Created by Jackson on 11/26/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "LoginSignup.h"

@interface LoginSignup () <UITextFieldDelegate>

@end

@implementation LoginSignup

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        // Custom initialization
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
  
  
  // page customization based on type
  if( self.typePage ){
    [self.signUpInfoView setHidden:NO];
    [self.moveForward setTitle:@"SIGN UP" forState:UIControlStateNormal];
  }
  else{
    [self.logInInfoView setHidden:NO];
    [self.moveForward setTitle:@"LOG IN" forState:UIControlStateNormal];
  }
  
  // sets placeholder text color
  self.loginEmail.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"Email" attributes:@{NSForegroundColorAttributeName: [UIColor darkGrayColor]}];
  self.loginPassword.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"Password" attributes:@{NSForegroundColorAttributeName: [UIColor darkGrayColor]}];
  self.signupName.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"Name" attributes:@{NSForegroundColorAttributeName: [UIColor darkGrayColor]}];
  self.signupUsername.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"Username" attributes:@{NSForegroundColorAttributeName: [UIColor darkGrayColor]}];
  self.signupEmail.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"Email Address" attributes:@{NSForegroundColorAttributeName: [UIColor darkGrayColor]}];
  self.signupPass.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"Password" attributes:@{NSForegroundColorAttributeName: [UIColor darkGrayColor]}];
  self.signupPass2.attributedPlaceholder = [[NSAttributedString alloc] initWithString:@"Confirm Password" attributes:@{NSForegroundColorAttributeName: [UIColor darkGrayColor]}];
  
  
  // sets background of the move forward button
  CAGradientLayer *gradient = [CAGradientLayer layer];
  [gradient setFrame:CGRectMake(0, 0, 160, 40)];
  [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
  [self.buttonBack.layer insertSublayer:gradient atIndex:0];
  [self.buttonBack.layer setBorderColor: [UIColor darkGrayColor].CGColor];
  [self.buttonBack.layer setBorderWidth:1];
  
  // creates back button
  [self.moveBackward.layer setBorderWidth:1];
  [self.moveBackward.layer setBorderColor:[UIColor lightGrayColor].CGColor];
  [self.moveBackward.layer setCornerRadius:7];
  
  // sets default info if user wants their info remembered.
  NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
  if( [defaults objectForKey:@"email"] != NULL ) [self.loginEmail setText:[defaults objectForKey:@"email"]];
  if( [defaults objectForKey:@"pass"] != NULL ) [self.loginPassword setText:[defaults objectForKey:@"pass"]];
  
  // setting border around text fields.
  [self.loginPassword.layer setBorderWidth:1];
  [self.loginPassword.layer setBorderColor:[UIColor darkGrayColor].CGColor];
  [self.loginPassword.layer setCornerRadius:5];
  [self.loginEmail.layer setBorderWidth:1];
  [self.loginEmail.layer setBorderColor:[UIColor darkGrayColor].CGColor];
  [self.loginEmail.layer setCornerRadius:5];
  [self.signupName.layer setBorderWidth:1];
  [self.signupName.layer setBorderColor:[UIColor darkGrayColor].CGColor];
  [self.signupName.layer setCornerRadius:5];
  [self.signupUsername.layer setBorderWidth:1];
  [self.signupUsername.layer setBorderColor:[UIColor darkGrayColor].CGColor];
  [self.signupUsername.layer setCornerRadius:5];
  [self.signupEmail.layer setBorderWidth:1];
  [self.signupEmail.layer setBorderColor:[UIColor darkGrayColor].CGColor];
  [self.signupEmail.layer setCornerRadius:5];
  [self.signupPass.layer setBorderWidth:1];
  [self.signupPass.layer setBorderColor:[UIColor darkGrayColor].CGColor];
  [self.signupPass.layer setCornerRadius:5];
  [self.signupPass2.layer setBorderWidth:1];
  [self.signupPass2.layer setBorderColor:[UIColor darkGrayColor].CGColor];
  [self.signupPass2.layer setCornerRadius:5];
  
  // creates the error handler for text entry
  self.handler = [[ErrorHandler alloc] init];
  
  [self setRaised:NO];
  [self setEditing:NO];
}

-(void) viewWillAppear:(BOOL)animated{
  // sets notifications for keyboard showing.
  [[NSNotificationCenter defaultCenter] addObserver:self
                                           selector:@selector(orientationChanged)
                                               name:UIDeviceOrientationDidChangeNotification
                                             object:nil];
  UISwipeGestureRecognizer *swipeDown = [[UISwipeGestureRecognizer alloc] initWithTarget:self
                                                                                  action:@selector(endEditing)];
  [swipeDown setDirection:UISwipeGestureRecognizerDirectionDown];
  [self.view addGestureRecognizer:swipeDown];
}
-(void) viewDidDisappear:(BOOL)animated{
  [[NSNotificationCenter defaultCenter] removeObserver:self
                                                  name:UIDeviceOrientationDidChangeNotification
                                                object:nil];
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

-(bool)emailFormat:(NSString *)string{
  BOOL stricterFilter = YES; // Discussion http://blog.logichigh.com/2010/09/02/validating-an-e-mail-address/
  NSString *stricterFilterString = @"[A-Z0-9a-z\\._%+-]+@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}";
  NSString *laxString = @".+@([A-Za-z0-9]+\\.)+[A-Za-z]{2}[A-Za-z]*";
  NSString *emailRegex = stricterFilter ? stricterFilterString : laxString;
  NSPredicate *emailTest = [NSPredicate predicateWithFormat:@"SELF MATCHES %@", emailRegex];
  NSLog(@"%i",[emailTest evaluateWithObject:string]);
  return [emailTest evaluateWithObject:string];
}

-(IBAction)next:(id)sender{
  if( !self.typePage ){
    if( ![self emailFormat:self.loginEmail.text] ) [self.handler handle:1];
    else if( [self.loginPassword.text isEqualToString:@""] ) [self.handler handle:2];
    else{
      // NO ERRORS.
      NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
      if( self.rememberInfo.isOn ){
        [defaults setObject:self.loginEmail.text forKey:@"email"];
        [defaults setObject:self.loginPassword.text forKey:@"pass"];
      }
      else{
        [defaults setObject:NULL forKey:@"email"];
        [defaults setObject:NULL forKey:@"pass"];
      }
      [defaults synchronize];
      
      mapPage *newVC = [[mapPage alloc] init];
      [self.navigationController pushViewController:newVC animated:YES];
    }
  }
  else{
    if( [self.signupName.text isEqualToString:@""] || [self.signupEmail.text isEqualToString:@""] ||
       [self.signupUsername.text isEqualToString:@""] || [self.signupPass.text isEqualToString:@""] ||
       [self.signupPass2.text isEqualToString:@""] )
      [self.handler handle:2];
    else if( [self.signupUsername.text length] < 4 )
      [self.handler handle:4];
    else if( ![self emailFormat:self.signupEmail.text] )
      [self.handler handle:1];
    else if( ([self.signupPass.text length] < 4) || ![self.signupPass.text isEqualToString:self.signupPass2.text])
      [self.handler handle:3];
    else{
      // NO ERRORS.
      NSUserDefaults *defaults = [NSUserDefaults standardUserDefaults];
      if( self.rememberInfo.isOn ){
        [defaults setObject:self.signupEmail.text forKey:@"email"];
        [defaults setObject:self.signupPass.text forKey:@"pass"];
      }
      else{
        [defaults setObject:NULL forKey:@"email"];
        [defaults setObject:NULL forKey:@"pass"];
      }
      [defaults setObject:@"1" forKey:@"sortBy"];
      [defaults synchronize];
      
      mapPage *newVC = [[mapPage alloc] init];
      [self.navigationController pushViewController:newVC animated:YES];
    }
  }
}

-(void)orientationChanged{
  if( self.typePage ){
    UIInterfaceOrientation orientation = [[UIApplication sharedApplication] statusBarOrientation];
    NSInteger n[3];
    if( UIInterfaceOrientationIsPortrait(orientation)){
      if( self.editing ) n[0] = 430, n[1] = 370, n[2] = 257;
      else n[0] = 500, n[1] = 440, n[2] = 327;
    }
    else{
      if( self.editing ) n[0] = 220, n[1] = 160, n[2] = 47;
      else n[0] = 290, n[1] = 230, n[2] = 117;
    }
    [self.buttonBack setCenter:CGPointMake(self.view.center.x, n[0])];
    [self.toggleInfoView setCenter:CGPointMake(self.view.center.x, n[1])];
    [self.signUpInfoView setCenter:CGPointMake(self.view.center.x, n[2])];
  }
}

-(IBAction)back:(id)sender{
  [self.navigationController popViewControllerAnimated:YES];
}

-(BOOL)textFieldShouldReturn:(UITextField *)textField{
  if( (textField.tag == 2) || (textField.tag == 7) ) [textField resignFirstResponder], [self setRaised:NO];
  else [[self.view viewWithTag:(textField.tag+1)] becomeFirstResponder];
  return YES;
  
}
-(void)textFieldDidBeginEditing:(UITextField *)textField{
//  NSLog(@"BEGIN... %li", textField.tag);
//  if( self.raised ){
//    [UIView animateWithDuration:0.2 animations:^{
//      if( textField.tag <= 2 || textField.tag >= 5 ){
//        [self.logInInfoView setCenter:CGPointMake(self.logInInfoView.center.x, self.logInInfoView.center.y-60)];
//        [self.signUpInfoView setCenter:CGPointMake(self.signUpInfoView.center.x, self.signUpInfoView.center.y-60)];
//        [self.buttonBack setCenter:CGPointMake(self.buttonBack.center.x, self.buttonBack.center.y-60)];
//        [self.toggleInfoView setCenter:CGPointMake(self.toggleInfoView.center.x, self.toggleInfoView.center.y-60)];
//        [self setRaised:YES];
//      }
//      else [self setRaised:NO];
//    }];
//    [self setEditing:YES];
//  }
}
-(void)textFieldDidEndEditing:(UITextField *)textField{

}
//-(BOOL)textFieldShouldBeginEditing:(UITextField *)textField{
//  UIInterfaceOrientation orientation = [[UIApplication sharedApplication] statusBarOrientation];
//  if( UIInterfaceOrientationIsPortrait(orientation)){
//    if(( textField.tag <=2 || textField.tag >= 5 ) && (self.buttonBack.center.y == 327)){
//      
//    }
//  }
//  else{
//    
//  }
//}


-(void)endEditing{
  [self.view endEditing:YES];
  [self setRaised:NO];
}
@end
