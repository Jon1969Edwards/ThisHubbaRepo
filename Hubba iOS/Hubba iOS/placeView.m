//
//  placeView.m
//  Hubba iOS
//
//  Created by Jackson on 11/24/13.
//  Copyright (c) 2013 eecs499. All rights reserved.
//

#import "placeView.h"

@interface placeView () <UICollectionViewDataSource, UICollectionViewDelegate, UIActionSheetDelegate, UIImagePickerControllerDelegate, UINavigationControllerDelegate, UIAlertViewDelegate>

@end

@implementation placeView

// ----------------------------------------------------------------
// UICollectionView Stuff
-(NSInteger)numberOfSectionsInCollectionView:(UICollectionView *)collectionView {
  return 1;
}
- (NSInteger)collectionView:(UICollectionView *)collectionView numberOfItemsInSection:(NSInteger)section {
  return objects.count;
}
- (UICollectionViewCell *)collectionView:(UICollectionView *)cv cellForItemAtIndexPath:(NSIndexPath *)indexPath; {
  Cell *cell = [cv dequeueReusableCellWithReuseIdentifier:@"MY_CELL" forIndexPath:indexPath];
  
  if(cell == nil) NSLog(@"%li", (long)indexPath.row);

  [cell setImg:[UIImage imageNamed:[objects objectAtIndex:indexPath.row]]];
  return cell;
}
-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
  NSLog(@"Item at: %li", (long)indexPath.row);
}
// ----------------------------------------------------------------



// ----------------------------------------------------------------
// UIActionSheet Stuff
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
  switch (buttonIndex) {
    case 0:
      [self takeNewPhotoFromCamera];
      break;
    case 1:
      [self choosePhotoFromExistingImages];
    default:
      break;
  }
}
- (void)takeNewPhotoFromCamera
{
  if ([UIImagePickerController isSourceTypeAvailable: UIImagePickerControllerSourceTypeCamera])
  {
    UIImagePickerController *controller = [[UIImagePickerController alloc] init];
    controller.sourceType = UIImagePickerControllerSourceTypeCamera;
    controller.allowsEditing = YES;
    controller.mediaTypes = [UIImagePickerController availableMediaTypesForSourceType: UIImagePickerControllerSourceTypeCamera];
    controller.delegate = self;
    [self.navigationController presentViewController: controller animated: YES completion: nil];
  }
}
-(void)choosePhotoFromExistingImages
{
  if ([UIImagePickerController isSourceTypeAvailable: UIImagePickerControllerSourceTypePhotoLibrary])
  {
    UIImagePickerController *controller = [[UIImagePickerController alloc] init];
    controller.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
    controller.allowsEditing = NO;
    controller.mediaTypes = [UIImagePickerController availableMediaTypesForSourceType: UIImagePickerControllerSourceTypeSavedPhotosAlbum];
    controller.delegate = self;
    [self.navigationController presentViewController: controller animated: YES completion: nil];
  }
}
- (void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
  UIImage *image = [info valueForKey: UIImagePickerControllerOriginalImage];
  self.imgData = UIImageJPEGRepresentation(image, 0.1);
  UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Are you sure?"
                                                  message:@""
                                                 delegate:self
                                        cancelButtonTitle:@"Cancel"
                                        otherButtonTitles:@"Submit", nil];
  [alert show];
}
- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker;
{
  [self.navigationController dismissViewControllerAnimated: YES completion: nil];
}
// ----------------------------------------------------------------



// ----------------------------------------------------------------
// UIAlertView Stuff
- (void)alertView:(UIAlertView *)alertView didDismissWithButtonIndex:(NSInteger)buttonIndex
{
  if(buttonIndex == 1){
    [self.navigationController dismissViewControllerAnimated: YES completion: nil];
  }
}
// ----------------------------------------------------------------



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
    // Do any additional setup after loading the view from its nib.
  
    CAGradientLayer *gradient = [CAGradientLayer layer];
    [gradient setFrame:CGRectMake(0, 5, 104, 40)];
    [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
    [but1.layer insertSublayer:gradient atIndex:0];
    gradient = [CAGradientLayer layer];
    [gradient setFrame:CGRectMake(0, 5, 104, 40)];
    [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
    [but2.layer insertSublayer:gradient atIndex:0];
    gradient = [CAGradientLayer layer];
    [gradient setFrame:CGRectMake(0, 5, 104, 40)];
    [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
    [but3.layer insertSublayer:gradient atIndex:0];
    gradient = [CAGradientLayer layer];
    [gradient setFrame:CGRectMake(0, 0, 280, 40)];
    [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
    [but4.layer insertSublayer:gradient atIndex:0];
  but1.layer.borderWidth = but2.layer.borderWidth = but3.layer.borderWidth = but4.layer.borderWidth = 1;
  but1.layer.borderColor = but2.layer.borderColor = but3.layer.borderColor = but4.layer.borderColor = [UIColor darkGrayColor].CGColor;
  
  [favorites setContentHorizontalAlignment:UIControlContentHorizontalAlignmentCenter];
  [favorites.titleLabel setTextAlignment:NSTextAlignmentCenter];
  
  objects = [[NSArray alloc] initWithObjects:@"IMG_2531.png",@"IMG_2533.png",@"IMG_2535.png",
             @"img.png",@"img2.png", @"IMG_2531.png",@"IMG_2533.png",@"IMG_2535.png",
             @"img.png",@"img2.png",nil];
  
  [self.picList.layer setBorderWidth:2];
  [self.picList.layer setBorderColor:[UIColor darkGrayColor].CGColor];
  [self.picList registerClass:[Cell class] forCellWithReuseIdentifier:@"MY_CELL"];
  
  UISwipeGestureRecognizer *swipeGesture = [[UISwipeGestureRecognizer alloc] initWithTarget:self action:@selector(goBack:)];
  [swipeGesture setDirection:UISwipeGestureRecognizerDirectionDown];
  [self.view addGestureRecognizer:swipeGesture];
  
}

- (void)didReceiveMemoryWarning
{
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)addImage:(id)sender
{
  UIActionSheet *actionSheet = [[UIActionSheet alloc] initWithTitle: nil
                                                           delegate: self
                                                  cancelButtonTitle: @"Cancel"
                                             destructiveButtonTitle: nil
                                                  otherButtonTitles: @"Take a new photo", @"Choose from existing", nil];
  [actionSheet showInView:self.view];
}

-(void)goBack:(UIPanGestureRecognizer *)sender{
  [self.navigationController popViewControllerAnimated:YES];
}

@end
