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
@synthesize dist, bustLevel, diffLevel, placeName;

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
  [cell setBackgroundColor:self.view.backgroundColor];
  [cell setImg:[UIImage imageNamed:[objects objectAtIndex:indexPath.row]]];
  return cell;
}
-(UIImage *)scaleImage:(UIImageView *)image toSize:(CGSize)newSize {
  
  float width = newSize.width;
  float height = newSize.height;
  
  UIGraphicsBeginImageContext(newSize);
  CGRect rect = CGRectMake(0, 0, width, height);
  
  float widthRatio = image.image.size.width / width;
  float heightRatio = image.image.size.height / height;
  float divisor = widthRatio > heightRatio ? widthRatio : heightRatio;
  
  width = image.image.size.width / divisor;
  height = image.image.size.height / divisor;
  
  rect.size.width  = width;
  rect.size.height = height;
  
  if(height < width)
    rect.origin.y = height / 3;
  [image.image drawInRect: rect];
  
  UIImage *smallImage = UIGraphicsGetImageFromCurrentImageContext();
  UIGraphicsEndImageContext();
  
  return smallImage;
}
-(void)collectionView:(UICollectionView *)collectionView didSelectItemAtIndexPath:(NSIndexPath *)indexPath{
  NSLog(@"Item at: %li", (long)indexPath.row);
  Cell *cell = (Cell *)[collectionView cellForItemAtIndexPath:indexPath];
  
  self.fullScreenImage = [[UIImageView alloc] initWithImage:cell.pic.image];
  [self.fullScreenImage setContentMode:UIViewContentModeScaleAspectFit];
  [self.fullScreenImage setFrame:[self.view convertRect:cell.pic.frame
                                             fromView: cell.pic.superview]];
  origFrame = self.fullScreenImage.frame;
  [self.fullScreenImage setUserInteractionEnabled:YES];
  [self.fullScreenImage setClipsToBounds:YES];
  
  objc_setAssociatedObject( self.fullScreenImage,
                           "original_frame",
                           [NSValue valueWithCGRect: self.fullScreenImage.frame],
                           OBJC_ASSOCIATION_RETAIN);
  
  [UIView transitionWithView: self.view
                    duration: 0.25
                     options: UIViewAnimationOptionAllowAnimatedContent
                  animations:^{
                    
                    [self.view addSubview: self.fullScreenImage];
                    [self.fullScreenImage setFrame:self.view.bounds];
                    [self.fullScreenImage setBackgroundColor:[UIColor blackColor]];
                  } completion:^(BOOL finished) {
                    
                    UITapGestureRecognizer *tgr = [[UITapGestureRecognizer alloc] initWithTarget:self
                                                                                          action:@selector(shrink)];
                    UISwipeGestureRecognizer *left = [[UISwipeGestureRecognizer alloc] initWithTarget:self
                                                                                               action:@selector(left)
                                                    ];
                    UISwipeGestureRecognizer *right = [[UISwipeGestureRecognizer alloc] initWithTarget:self
                                                                                               action:@selector(right)
                                                      ];
                    [left setDirection:UISwipeGestureRecognizerDirectionLeft];
                    [right setDirection:UISwipeGestureRecognizerDirectionRight];
                    
                    [self.fullScreenImage addGestureRecognizer: left];
                    [self.fullScreenImage addGestureRecognizer: right];
                    [self.fullScreenImage addGestureRecognizer: tgr];
                    rowShowing = indexPath.row;
                    
                  }];
}

// ----------------------------------------------------------------



// ----------------------------------------------------------------
// FUNCTIONS FOR FULL SCREEN IMAGE
-(void)shrink{
  [UIView transitionWithView: self.view
                    duration: 0.25
                     options: UIViewAnimationOptionAllowAnimatedContent
                  animations:^{
                    [self.fullScreenImage setBackgroundColor:[UIColor clearColor]];
                    [self.fullScreenImage setFrame:origFrame];
                  } completion:^(BOOL finished) {
                    [self.fullScreenImage removeFromSuperview];
                  }];
}
-(void)left{
  if( rowShowing+1 < [objects count] ){
    UIImage *img = [UIImage imageNamed:[objects objectAtIndex:++rowShowing]];
    [self.picList scrollToItemAtIndexPath:[NSIndexPath indexPathForRow:rowShowing inSection:0]
                         atScrollPosition:UICollectionViewScrollPositionBottom animated:YES];
    UIImageView *newView = [[UIImageView alloc] initWithImage:img];
    [newView setContentMode:UIViewContentModeScaleAspectFit];
    [newView setFrame:self.fullScreenImage.frame];
    [newView setCenter:CGPointMake(newView.center.x+320, newView.center.y)];
    [newView setUserInteractionEnabled:YES];
    [newView setClipsToBounds:YES];
    [newView setBackgroundColor:[UIColor blackColor]];
    UITapGestureRecognizer *tgr = [[UITapGestureRecognizer alloc] initWithTarget:self
                                                                          action:@selector(shrink)];
    UISwipeGestureRecognizer *left = [[UISwipeGestureRecognizer alloc] initWithTarget:self
                                                                               action:@selector(left)];
    UISwipeGestureRecognizer *right = [[UISwipeGestureRecognizer alloc] initWithTarget:self
                                                                                action:@selector(right)];
    
    [left setDirection:UISwipeGestureRecognizerDirectionLeft];
    [right setDirection:UISwipeGestureRecognizerDirectionRight];
    
    [newView addGestureRecognizer: left];
    [newView addGestureRecognizer: right];
    [newView addGestureRecognizer: tgr];
    
    [UIView transitionWithView: self.view
                      duration: 0.25
                       options: UIViewAnimationOptionAllowAnimatedContent
                    animations:^{
                      [self.view addSubview:newView];
                      [self.fullScreenImage setCenter:CGPointMake(self.fullScreenImage.center.x-320, self.fullScreenImage.center.y)];
                      [newView setCenter:CGPointMake(newView.center.x-320, newView.center.y)];
                      
                    } completion:^(BOOL finished) {
                      [self.fullScreenImage removeFromSuperview];
                      self.fullScreenImage = newView;

                      Cell *cell = (Cell *)[self.picList cellForItemAtIndexPath:[NSIndexPath indexPathForRow:rowShowing inSection:0]];
                      origFrame = [self.view convertRect:cell.pic.frame
                                    fromView: cell.pic.superview];
                    }];
    
  } else return;
}
-(void)right{
  if(rowShowing){
    UIImage *img = [UIImage imageNamed:[objects objectAtIndex:--rowShowing]];
    [self.picList scrollToItemAtIndexPath:[NSIndexPath indexPathForRow:rowShowing inSection:0]
                         atScrollPosition:UICollectionViewScrollPositionBottom animated:YES];
    UIImageView *newView = [[UIImageView alloc] initWithImage:img];
    [newView setContentMode:UIViewContentModeScaleAspectFit];
    [newView setFrame:self.fullScreenImage.frame];
    [newView setCenter:CGPointMake(newView.center.x-320, newView.center.y)];
    [newView setUserInteractionEnabled:YES];
    [newView setClipsToBounds:YES];
    [newView setBackgroundColor:[UIColor blackColor]];
    UITapGestureRecognizer *tgr = [[UITapGestureRecognizer alloc] initWithTarget:self
                                                                          action:@selector(shrink)];
    UISwipeGestureRecognizer *left = [[UISwipeGestureRecognizer alloc] initWithTarget:self
                                                                               action:@selector(left)];
    UISwipeGestureRecognizer *right = [[UISwipeGestureRecognizer alloc] initWithTarget:self
                                                                                action:@selector(right)];
    
    [left setDirection:UISwipeGestureRecognizerDirectionLeft];
    [right setDirection:UISwipeGestureRecognizerDirectionRight];
    
    [newView addGestureRecognizer: left];
    [newView addGestureRecognizer: right];
    [newView addGestureRecognizer: tgr];
    
    [UIView transitionWithView: self.view
                      duration: 0.25
                       options: UIViewAnimationOptionAllowAnimatedContent
                    animations:^{
                      [self.view addSubview:newView];
                      [self.fullScreenImage setCenter:CGPointMake(self.fullScreenImage.center.x+320, self.fullScreenImage.center.y)];
                      [newView setCenter:CGPointMake(newView.center.x+320, newView.center.y)];
                      
                    } completion:^(BOOL finished) {
                      [self.fullScreenImage removeFromSuperview];
                      self.fullScreenImage = newView;
                      
                      Cell *cell = (Cell *)[self.picList cellForItemAtIndexPath:[NSIndexPath indexPathForRow:rowShowing inSection:0]];
                      origFrame = [self.view convertRect:cell.pic.frame
                                                fromView: cell.pic.superview];
                    }];
    
  } else return;
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
  [self.placeName setText: self.helpName.text];
  [self.dist setText: self.helpDist.text];
  [self.diffLevel setText: self.helpDiff.text];
  [self.bustLevel setText: self.helpBust.text];
  
    [self.placeImage setImage:[UIImage imageNamed:@"default"]];
  
    CAGradientLayer *gradient = [CAGradientLayer layer];
    [gradient setFrame:CGRectMake(0, 5, 104, 40)];
    [gradient setColors:[NSArray arrayWithObjects:(id)[UIColor blackColor].CGColor, (id)GRAY2.CGColor, (id)[UIColor blackColor].CGColor, nil]];
    [but0.layer insertSublayer:gradient atIndex:0];
    gradient = [CAGradientLayer layer];
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
  but0.layer.borderWidth = but1.layer.borderWidth = but2.layer.borderWidth = but3.layer.borderWidth = but4.layer.borderWidth = 1;
  but0.layer.borderColor = but1.layer.borderColor = but2.layer.borderColor = but3.layer.borderColor = but4.layer.borderColor = [UIColor darkGrayColor].CGColor;
  
  [favorites setContentHorizontalAlignment:UIControlContentHorizontalAlignmentCenter];
  [favorites.titleLabel setTextAlignment:NSTextAlignmentCenter];
  [viewonmap.titleLabel setTextAlignment:NSTextAlignmentCenter];
  
  objects = [[NSArray alloc] initWithObjects:@"01.jpg", @"02.jpg", @"03.jpg", @"04.jpg", @"05.jpg", @"06.jpg", @"07.jpg", @"08.jpg", @"09.jpg", @"10.jpg", nil];
  
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
- (IBAction)done:(id)sender{
  [self popController];
}
- (IBAction)viewOnMap:(id)sender{
  [self.delegate sendDataBack:[NSArray arrayWithObjects:@"42.2814", @"-83.7483", nil]];
  [self popController];
}
-(void) popController{
  [self.navigationController popViewControllerAnimated:YES];
}


@end
